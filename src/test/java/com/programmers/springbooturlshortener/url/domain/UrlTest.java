package com.programmers.springbooturlshortener.url.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.programmers.springbooturlshortener.global.domain.exception.EntityNotFound;
import com.programmers.springbooturlshortener.global.domain.exception.InvalidUrlRequest;
import com.programmers.springbooturlshortener.global.domain.utils.PrefixModifier;
import com.programmers.springbooturlshortener.url.presentation.request.UrlCreate;

class UrlTest {

	@DisplayName("새로운 Url 생성 테스트 - 성공")
	@ParameterizedTest
	@CsvSource({
		"http://www.naver.com",
		"https://www.naver.com",
		"www.naver.com",
		"naver.com",
	})
	void url_create_success(String originUrl) {
	    // given
		String prefixModifyUrl = PrefixModifier.modifyUrlPrefix(originUrl);
		UrlCreate urlCreate = new UrlCreate(prefixModifyUrl);

	    // when
		Url url = Url.from(urlCreate);

	    // then
		assertThat(url.getOriginUrl()).isEqualTo(prefixModifyUrl);
	    assertThat(url.getRequestCount()).isZero();
	}

	@DisplayName("잘못된 Url 생성 시, 예외 던지는 지")
	@ParameterizedTest
	@CsvSource({
		"ftp://www.naver.com",
		"http:/www.naver.com"
	})
	void url_create_fail(String originUrl) {
		// given
		UrlCreate urlCreate = new UrlCreate(originUrl);

		// when, then
		assertThatThrownBy(() -> Url.from(urlCreate))
			.isInstanceOf(InvalidUrlRequest.class)
			.hasMessage(new InvalidUrlRequest().getMessage());
	}

	@DisplayName("단축된 Url 키 업데이트 - 성공")
	@Test
	void short_url_key_update_success() {
	    // given
		Url url = Url.builder()
			.urlId(1L)
			.originUrl("https://www.naver.com")
			.build();

	    // when
		url.updateShortUrlKey("ABCDEF");

	    // then
	    assertThat(url.getShortUrlKey()).isEqualTo("ABCDEF");
	}

	@DisplayName("저장된 Url 없는 경우, 단축된 Url 키 업데이트 시 예외 던지는지")
	@Test
	void short_url_key_update_fail() {
		// given
		Url url = Url.builder()
			.originUrl("https://www.naver.com")
			.build();

		// when, then
		assertThatThrownBy(() -> url.updateShortUrlKey("ABCDEF"))
			.isInstanceOf(EntityNotFound.class)
			.hasMessage(new EntityNotFound().getMessage());
	}

	@DisplayName("Url 요청 조회수 증가 - 성공")
	@Test
	void increase_request_count_success() {
	    // given
		Url url = Url.builder()
			.originUrl("https://www.naver.com")
			.requestCount(0L)
			.build();

	    // when
		url.increaseRequestCount();
		url.increaseRequestCount();

	    // then
		assertThat(url.getRequestCount()).isEqualTo(2L);
	}
}
