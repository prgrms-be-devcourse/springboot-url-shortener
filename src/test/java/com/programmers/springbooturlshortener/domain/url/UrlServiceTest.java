package com.programmers.springbooturlshortener.domain.url;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.springbooturlshortener.domain.algorithm.Base62Algorithm;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceResponseDto;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

	@InjectMocks
	UrlService urlService;

	@Mock
	UrlRepository urlRepository;

	@Mock
	Base62Algorithm base62Algorithm;

	@Mock
	Url savedUrl;

	private UrlServiceRequestDto urlServiceRequestDto = new UrlServiceRequestDto("www.google.com", "BASE62");
	private Long savedUrlId = 1L;
	private String shortUrl = "AAAAAAB";

	@Test
	@DisplayName("UrlService 테스트: originUrl 의 첫 요청일 때, shortUrl 생성에 성공한다.")
	void createShortUrlWhenFirstRequestTest() {
		// given
		when(urlRepository.findByOriginUrl(urlServiceRequestDto.originUrl())).thenReturn(Optional.empty());
		when(urlRepository.save(any(Url.class))).thenReturn(savedUrl);
		when(savedUrl.getId()).thenReturn(savedUrlId);
		when(base62Algorithm.encode(savedUrlId)).thenReturn(shortUrl);
		when(savedUrl.getOriginUrl()).thenReturn(urlServiceRequestDto.originUrl());

		// when
		UrlServiceResponseDto urlServiceResponseDto = urlService.createShortUrl(urlServiceRequestDto);

		// then
		verify(urlRepository).findByOriginUrl(urlServiceRequestDto.originUrl());
		verify(urlRepository).save(any(Url.class));
		verify(savedUrl).getId();
		verify(base62Algorithm).encode(savedUrl.getId());
		verify(savedUrl).getOriginUrl();
		assertThat(urlServiceResponseDto).hasFieldOrPropertyWithValue("originUrl", urlServiceRequestDto.originUrl())
			.hasFieldOrPropertyWithValue("shortUrl", shortUrl)
			.hasFieldOrPropertyWithValue("requestCount", 1L);
	}

	@Test
	@DisplayName("UrlService 테스트: 입력된 shortUrl 값으로 저장된 Url 의 originUrl 조회에 성공한다.")
	void successGetOriginUrlTest() {
		// given
		Long requestCount = 2L;
		when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(savedUrl));
		when(savedUrl.getOriginUrl()).thenReturn(urlServiceRequestDto.originUrl());
		when(savedUrl.getShortUrl()).thenReturn(shortUrl);
		when(savedUrl.getRequestCount()).thenReturn(requestCount);

		// when
		UrlServiceResponseDto urlServiceResponseDto = urlService.getOriginUrl(shortUrl);

		// then
		verify(urlRepository).findByShortUrl(shortUrl);
		verify(savedUrl).getOriginUrl();
		verify(savedUrl).getShortUrl();
		verify(savedUrl).getRequestCount();
		assertThat(urlServiceResponseDto).hasFieldOrPropertyWithValue("originUrl", urlServiceRequestDto.originUrl())
			.hasFieldOrPropertyWithValue("shortUrl", shortUrl)
			.hasFieldOrPropertyWithValue("requestCount", requestCount);
	}

	@Test
	@DisplayName("UrlService 테스트: 입력된 shortUrl 값으로 저장된 originUrl 이 없으면  EntityNotFoundException 를 던지며 실패한다.")
	void failGetOriginUrlTest() {
		// given
		when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.empty());

		// when & then
		try {
			urlService.getOriginUrl(shortUrl);
		} catch (Exception e) {
			verify(urlRepository).findByShortUrl(shortUrl);
			assertThat(e).isInstanceOf(EntityNotFoundException.class);
			return;
		}

		fail("입력된 shortUrl 값으로 저장된 originUrl 이 없는데 EntityNotFoundException 이 터지지 않음.");
	}
}
