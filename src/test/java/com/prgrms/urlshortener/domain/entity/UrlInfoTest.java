package com.prgrms.urlshortener.domain.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlInfoTest {

	@Test
	@DisplayName("HTTPS://로 시작하는 주소 필터 테스트")
	void remove_https_test() {
	    // given, when
		UrlInfo urlInfo = new UrlInfo("https://www.google.com");

	    // then
		assertThat(urlInfo.getOriginalUrl()).isEqualTo("www.google.com");
	}

	@Test
	@DisplayName("HTTP://로 시작하는 주소 필터 테스트")
	void remove_http_test() {
		// given, when
		UrlInfo urlInfo = new UrlInfo("http://www.google.com");

		// then
		assertThat(urlInfo.getOriginalUrl()).isEqualTo("www.google.com");
	}
}
