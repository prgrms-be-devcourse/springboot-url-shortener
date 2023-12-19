package com.dev.shortenerurl.url.application;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dev.shortenerurl.common.exception.CommonException;
import com.dev.shortenerurl.supports.fixture.UrlFixture;
import com.dev.shortenerurl.url.domain.IdGenerator;
import com.dev.shortenerurl.url.domain.UrlRepository;
import com.dev.shortenerurl.url.domain.model.EncodingAlgorithms;
import com.dev.shortenerurl.url.domain.model.Url;
import com.dev.shortenerurl.url.dto.response.OriginUrlInfo;
import com.dev.shortenerurl.url.dto.response.ShortenUrlInfo;

@DisplayName("[UrlService 테스트]")
@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

	@InjectMocks
	private UrlService urlService;
	@Mock
	private UrlRepository urlRepository;
	@Mock
	private IdGenerator idGenerator;

	private void assertShortenUrl(String expectedUrl, ShortenUrlInfo shortenUrlInfo) {
		String shortenUrl = shortenUrlInfo.shortenUrl();
		int shortenUrlStartIndex = shortenUrl.lastIndexOf("/");
		String encodedUrl = shortenUrl.substring(shortenUrlStartIndex + 1);
		String urlPrefix = shortenUrl.substring(0, shortenUrlStartIndex);

		assertThat(encodedUrl).isEqualTo(expectedUrl);
		assertThat(urlPrefix).isEqualTo("http://localhost:8080/url");
		assertThat(shortenUrlInfo.requestCount()).isZero();
	}

	@Nested
	@DisplayName("[shortenUrl 을 생성한다]")
	class createUrl {
		@Test
		@DisplayName("[이미 url 에 대한 shortenUrl 이 존재해서 기존 shortenUrl 을 리턴한다]")
		void createShortenUrl_1() {
			//given
			Url url = UrlFixture.getUrl();
			String originUrl = "http://localhost:8080/originUrl";
			given(urlRepository.findByOriginUrl(originUrl))
				.willReturn(Optional.of(url));

			//when
			ShortenUrlInfo shortenUrlInfo = urlService.createShortenUrl(originUrl, "BASE_62");

			//then
			assertShortenUrl(url.getEncodedUrl(), shortenUrlInfo);
		}

		@Test
		@DisplayName("[새로운 shortenUrl 생성하여 리턴한다]")
		void createShortenUrl_2() {
			//given
			String originUrl = "http://localhost:8080/originUrl";
			given(urlRepository.findByOriginUrl(originUrl))
				.willReturn(Optional.empty());
			given(idGenerator.get()).willReturn(100L);

			//when
			ShortenUrlInfo shortenUrlInfo = urlService.createShortenUrl(originUrl, "BASE_62");

			//then
			assertShortenUrl(EncodingAlgorithms.BASE_62.encode(100L), shortenUrlInfo);
		}
	}

	@Nested
	@DisplayName("[encodedUrl 에 해당하는 originUrl 정보를 가져온다]")
	class getOriginUrl {
		@Test
		@DisplayName("[성공적으로 originUrl 정보를 가져온다]")
		void getOriginUrl_1() {
			//given
			String encodedUrl = "encodedUrl";
			Url url = new Url("originUrl", 100L, "BASE_62");
			given(urlRepository.findByEncodedUrl(encodedUrl))
				.willReturn(Optional.of(url));

			//when
			OriginUrlInfo originUrlInfo = urlService.getOriginUrl(encodedUrl);

			//then
			assertThat(originUrlInfo.originUrl()).isEqualTo(url.getOriginUrl());
			assertThat(url.getRequestCount()).isEqualTo(1);
		}

		@Test
		@DisplayName("[encodedUrl 에 해당하는 originUrl 이 존재하지 않아 실패한다]")
		void getOriginUrl_2() {
			//given
			String encodedUrl = "encodedUrl";
			given(urlRepository.findByEncodedUrl(encodedUrl))
				.willReturn(Optional.empty());

			//when
			ThrowingCallable when = () -> urlService.getOriginUrl(encodedUrl);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommonException.class)
				.hasMessageContaining("shortenUrl 에 해당되는 url 이 없습니다");
		}
	}
}