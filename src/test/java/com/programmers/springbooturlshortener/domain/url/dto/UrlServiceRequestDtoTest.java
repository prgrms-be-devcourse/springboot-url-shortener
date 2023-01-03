package com.programmers.springbooturlshortener.domain.url.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.springbooturlshortener.domain.url.Url;

class UrlServiceRequestDtoTest {

	@Test
	@DisplayName("UrlServiceRequestDto 테스트: UrlServiceRequestDto 를 Url 로 변환한다.")
	void toEntityTest() {
		// given
		UrlServiceRequestDto urlServiceRequestDto = new UrlServiceRequestDto("www.naver.com", "BASE62");

		// when
		Url url = urlServiceRequestDto.toEntity();

		// then
		assertThat(url).hasFieldOrPropertyWithValue("id", null)
			.hasFieldOrPropertyWithValue("originUrl", urlServiceRequestDto.originUrl())
			.hasFieldOrPropertyWithValue("shortUrl", null)
			.hasFieldOrPropertyWithValue("algorithm", urlServiceRequestDto.algorithm())
			.hasFieldOrPropertyWithValue("requestCount", 1L);
	}
}