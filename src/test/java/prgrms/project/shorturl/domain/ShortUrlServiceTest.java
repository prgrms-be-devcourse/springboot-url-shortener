package prgrms.project.shorturl.domain;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import prgrms.project.shorturl.domain.ShortUrl;
import prgrms.project.shorturl.domain.ShortUrlDto.CreateDto;
import prgrms.project.shorturl.domain.ShortUrlRepository;
import prgrms.project.shorturl.domain.ShortUrlService;
import prgrms.project.shorturl.global.algorithm.ShortUrlGenerator;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceTest {

	@InjectMocks
	private ShortUrlService shortUrlService;

	@Mock
	private ShortUrlRepository shortUrlRepository;

	@Mock
	private ShortUrlGenerator shortUrlGenerator;

	@Mock
	private Map<String, ShortUrlGenerator> shortUrlGeneratorByBeanName;

	@Test
	@DisplayName("원본 url 정보를 입력받고 ShortUrl 을 생성한다.")
	void testCreateShortUrl() {
		//given
		var shortenUrl = "abcde123";
		var createDtoByBase62 = new CreateDto("https://google.com", "base62");
		var shortUrl = ShortUrl.createShortUrl(createDtoByBase62.originUrl(), shortenUrl);

		given(shortUrlGeneratorByBeanName.get(anyString())).willReturn(shortUrlGenerator);
		given(shortUrlGenerator.generate()).willReturn(shortenUrl);
		given(shortUrlRepository.save(any())).willReturn(shortUrl);

		//when
		var responseDto = shortUrlService.createShortUrl(createDtoByBase62);

		//then
		then(shortUrlRepository).should().save(any());
		assertThat(responseDto.originUrl()).isEqualTo(createDtoByBase62.originUrl());
	}

	@Test
	@DisplayName("아이디로 ShortUrl 엔티티를 조회한다.")
	void testGetShortUrl() {
		// given
		var shortUrl = createShortUrl();
		given(shortUrlRepository.findById(anyLong())).willReturn(Optional.of(shortUrl));

		// when
		var responseDto = shortUrlService.getShortUrl(1L);

		// then
		then(shortUrlRepository).should(times(1)).findById(1L);
		assertThat(responseDto.shortenUrl()).isEqualTo(shortUrl.getShortenUrl());
	}

	@Test
	@DisplayName("존재하지 아이디로 조회를 요청하는 경우 예외가 발생한다.")
	void testFailedToGetShortUrlByUnknownId() {
		//given
		given(shortUrlRepository.findById(anyLong())).willThrow(new EntityNotFoundException());

		//when, then
		assertThatThrownBy(
			() -> shortUrlService.getShortUrl(-1L)).isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	@DisplayName("리다이렉트 요청 시 numberOfRequests 숫자를 증가시킨다.")
	void testRedirectToOriginUrl() {
		//given
		var shortUrl = createShortUrl();
		var numberOfRequestsBeforeDoAction = shortUrl.getNumberOfRequests();
		given(shortUrlRepository.findById(anyLong())).willReturn(Optional.of(shortUrl));

		//when
		var responseDto = shortUrlService.redirectToOriginUrl(1L);

		//then
		then(shortUrlRepository).should().findById(1L);
		assertThat(responseDto.numberOfRequests()).isGreaterThan(numberOfRequestsBeforeDoAction);
	}

	private ShortUrl createShortUrl() {
		return ShortUrl.createShortUrl("https://google.com", "abcde123");
	}
}
