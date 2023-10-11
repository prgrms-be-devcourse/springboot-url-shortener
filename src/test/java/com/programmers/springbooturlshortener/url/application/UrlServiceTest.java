package com.programmers.springbooturlshortener.url.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.programmers.springbooturlshortener.global.domain.converter.AlgorithmConverter;
import com.programmers.springbooturlshortener.global.domain.converter.Base62Converter;
import com.programmers.springbooturlshortener.global.domain.utils.PrefixModifier;
import com.programmers.springbooturlshortener.url.domain.Url;
import com.programmers.springbooturlshortener.url.mock.FakeUrlRepository;
import com.programmers.springbooturlshortener.url.presentation.request.UrlCreate;

class UrlServiceTest {

	private UrlService urlService;

	@BeforeEach
	void init() {
		FakeUrlRepository fakeUrlRepository = new FakeUrlRepository();
		AlgorithmConverter algorithmConverter = new Base62Converter();
		this.urlService = new UrlService(fakeUrlRepository, algorithmConverter);

		fakeUrlRepository.save(Url.builder()
			.urlId(1L)
			.originUrl("https://www.naver.com")
			.build());
	}

	@DisplayName("새로운 Url 생성 테스트 - 성공")
	@ParameterizedTest
	@CsvSource({
		"http://www.naver.com",
		"www.naver.com",
		"naver.com",
	})
	void new_url_create_success(String originUrl) {
		// given
		String prefixModifyUrl = PrefixModifier.modifyUrlPrefix(originUrl);
		UrlCreate urlCreate = new UrlCreate(prefixModifyUrl);

		// when
		Url result = urlService.create(urlCreate);

		// then
		assertThat(result.getOriginUrl()).isEqualTo(prefixModifyUrl);
		assertThat(result.getShortUrlKey()).isEqualTo("B");
		assertThat(result.getRequestCount()).isEqualTo(0L);
	}

	@DisplayName("이미 존재하는 Url 인 경우 조회수 증가 - 성공")
	@ParameterizedTest
	@CsvSource({
		"https://www.naver.com"
	})
	void exists_url_create_success(String originUrl) {
		// given
		String prefixModifyUrl = PrefixModifier.modifyUrlPrefix(originUrl);
		UrlCreate urlCreate = new UrlCreate(prefixModifyUrl);
		Url url = urlService.create(urlCreate);

		// when
		Url result = urlService.create(urlCreate);

		// then
		assertThat(result.getOriginUrl()).isEqualTo(prefixModifyUrl);
		assertThat(result.getRequestCount()).isEqualTo(1L);
	}

	@DisplayName("단축 Url 이용 원본 Url 조회 - 성공")
	@ParameterizedTest
	@CsvSource({
		"http://localhost:8080/B",
		"B"
	})
	void get_origin_url_success(String shortUrl) {
		// given
		String prefixModifyUrl = PrefixModifier.modifyUrlPrefix("https://www.naver.com");
		UrlCreate urlCreate = new UrlCreate(prefixModifyUrl);
		Url url = urlService.create(urlCreate);

		String shortUrlKey = PrefixModifier.removeBeforeLastSlash(shortUrl);

		// when
		String result = urlService.getOriginUrl(shortUrlKey);

		// then
		assertThat(result).isEqualTo("https://www.naver.com");
	}

	@DisplayName("Url 조회 수 증가 테스트 - 성공")
	@Test
	void exists_url_increase_count_success() {
		// given
		UrlCreate urlCreate = new UrlCreate("http://www.naver.com");
		Url url = urlService.create(urlCreate);

		// when, then
		String result = urlService.getOriginUrl(url.getShortUrlKey());
		assertThat(url.getRequestCount()).isEqualTo(1L);
	}
}