package com.programmers.springbooturlshortener.domain.url;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.web.dto.UrlCreateDto;

class UrlTest {

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	@Test
	@DisplayName("Url increaseRequest() 테스트: increaseRequest 메서드를 호출하면 requestCount 필드값이 1 증가한다.")
	void increaseRequestCount() {
		// given
		Url url = Url.builder()
			.originUrl("www.naver.com")
			.algorithm("BASE62")
			.build();
		Long beforeRequestCount = url.getRequestCount();

		// when
		url.increaseRequestCount();

		// then
		assertThat(url.getRequestCount()).isEqualTo(beforeRequestCount + 1);
	}

	@Test
	@DisplayName("Url 검증 테스트: algorithm 필드 값으로 null 이 입력되면 Url 생성에 실패한다.")
	void failByNullAlgorithm() {
		// given
		Url url = Url.builder()
			.originUrl("www.naver.com")
			.algorithm(null)
			.build();

		// when
		Set<ConstraintViolation<Url>> constraintViolations = validator.validate(url);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	@DisplayName("Url 검증 테스트: originUrl 필드 값으로 blank, empty 가 입력되면 Url 생성에 실패한다.")
	void failByWrongOriginUrl(String originUrl) {
		// given
		Url url = Url.builder()
			.originUrl(originUrl)
			.algorithm("BASE62")
			.build();

		// when
		Set<ConstraintViolation<Url>> constraintViolations = validator.validate(url);

		// then
		assertThat(constraintViolations.size()).isEqualTo(1);
	}

	@ParameterizedTest
	@ValueSource(strings = {"https://www.naver.com", "http://www.google.com"})
	@DisplayName("Url 테스트: 입력 받는 origin URL 에서 프로토콜 값을 제거하고 Url 이 생성한다.")
	void successToRemoveProtocol(String originUrl) {
		// given
		UrlCreateDto urlCreateDto = new UrlCreateDto(originUrl, "BASE62");
		UrlServiceRequestDto urlServiceRequestDto = urlCreateDto.toUrlServiceRequestDto();
		String protocolRemovedUrl =
			originUrl.startsWith("https://") ?
				originUrl.replace("https://", "") : originUrl.replace("http://", "");

		// when
		Url url = urlServiceRequestDto.toEntity();

		// then
		assertThat(url.getOriginUrl().startsWith("https://")).isFalse();
		assertThat(url.getOriginUrl().startsWith("http://")).isFalse();
		assertThat(url).hasFieldOrPropertyWithValue("originUrl", protocolRemovedUrl)
			.hasFieldOrPropertyWithValue("algorithm", urlCreateDto.algorithm());
	}
}