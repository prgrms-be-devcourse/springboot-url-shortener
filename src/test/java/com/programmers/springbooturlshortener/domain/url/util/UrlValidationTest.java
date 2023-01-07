package com.programmers.springbooturlshortener.domain.url.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.programmers.springbooturlshortener.web.dto.UrlCreateDto;

class UrlValidationTest {

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@ParameterizedTest
	@ValueSource(strings = {
		"www.google.com",
		"www.naver.com",
		"https://github.com/prgrms-be-devcourse/springboot-url-shortener"
	})
	@DisplayName("Url 검증 테스트: 정상적인 URL 이 입력되면 검증을 통과한다.")
	void successValid(String url) {
		// given
		UrlCreateDto urlCreateDto = new UrlCreateDto(url, "BASE62");

		// when
		Set<ConstraintViolation<UrlCreateDto>> constraintViolations = validator.validate(urlCreateDto);

		// then
		Assertions.assertThat(constraintViolations).isEmpty();
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"abcdefg",
		"www.naver",
		"google"
	})
	@DisplayName("Url 검증 테스트: 잘못된 URL 이 입력되면 @UrlValid 에 의해 검증에 실패한다.")
	void failValidByUrlValid(String url) {
		// given
		UrlCreateDto urlCreateDto = new UrlCreateDto(url, "BASE62");

		// when
		Set<ConstraintViolation<UrlCreateDto>> constraintViolations = validator.validate(urlCreateDto);

		// then
		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@ValueSource(strings = {
		"",
		" ",
		"    "
	})
	@DisplayName("Url 검증 테스트: blank 나 empty URL 이 입력되면 @NotBlank 와 @UrlValid 에 의해 검증에 실패한다.")
	void failValidByNotBlank(String url) {
		// given
		UrlCreateDto urlCreateDto = new UrlCreateDto(url, "BASE62");

		// when
		Set<ConstraintViolation<UrlCreateDto>> constraintViolations = validator.validate(urlCreateDto);

		// then
		Assertions.assertThat(constraintViolations.size()).isEqualTo(2);
	}
}