package org.prgrms.url.shortner.springbooturlshortner.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.url.shortner.springbooturlshortner.domain.algorithm.RandomAlgorithm;
import org.prgrms.url.shortner.springbooturlshortner.domain.algorithm.ShortUrlAlgorithm;

@ExtendWith(MockitoExtension.class)
class UrlTest {

	@Mock
	private RandomAlgorithm randomAlgorithm;

	private void createUrl(String originUrl, ShortUrlAlgorithm shortUrlAlgorithm) {
		Url.builder()
			.originUrl(originUrl)
			.shortUrlAlgorithm(shortUrlAlgorithm)
			.build();
	}

	@ParameterizedTest
	@ValueSource(strings = {" "})
	@NullAndEmptySource
	void validateOriginUrlTest(String originUrl) {

		assertThrows(IllegalArgumentException.class, () -> createUrl(originUrl, randomAlgorithm));
	}

	@ParameterizedTest
	@MethodSource
	void validateShortUrlAlgorithm(String originUrl, ShortUrlAlgorithm shortUrlAlgorithm) {

		assertThrows(IllegalArgumentException.class, () -> createUrl(originUrl, shortUrlAlgorithm));
	}

	@ParameterizedTest
	@ValueSource(strings = {"naver.com", "google.com"})
	void createUrlTest(String originUrl) {

		assertDoesNotThrow(() -> createUrl(originUrl, randomAlgorithm));

	}

	public static Stream<Arguments> validateShortUrlAlgorithm() {
		return Stream.of(
			Arguments.arguments("naver.com", null),
			Arguments.arguments("google.com", null)
		);
	}

}
