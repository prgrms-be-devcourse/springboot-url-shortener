package com.programmers.springbooturlshortener.domain.url.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.programmers.springbooturlshortener.web.dto.UrlCreateDto;

class UrlCreateDtoTest {

	@ParameterizedTest
	@ValueSource(strings = {"https://www.naver.com", "http://www.google.com"})
	@DisplayName("UrlCreateDto 테스트: 입력 받는 origin URL 에서 프로토콜 값을 제거하고 UrlServiceRequestDto 로 변환한다.")
	void toUrlServiceRequestDtoTest(String url) {
		// given
		UrlCreateDto urlCreateDto = new UrlCreateDto(url, "BASE62");

		// when
		UrlServiceRequestDto urlServiceRequestDto = urlCreateDto.toUrlServiceRequestDto();

		// then
		assertThat(urlServiceRequestDto).hasFieldOrPropertyWithValue("originUrl", urlCreateDto.originUrl())
			.hasFieldOrPropertyWithValue("algorithm", urlCreateDto.algorithm());
	}
}