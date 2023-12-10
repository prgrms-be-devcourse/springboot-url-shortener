package com.dev.shortenerurl.url.domain.model;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[Url 테스트]")
class UrlTest {

	@Test
	@DisplayName("[Url 을 생성한다]")
	void newUrl() {
		//given
		String originUrl = "http://localhost:8080/hello";
		Long encodingId = 100L;
		String algorithm = "BASE_62";

		//when
		Url url = new Url(originUrl, encodingId, algorithm);

		//then
		assertAll(
			() -> assertThat(url.getId()).isEqualTo(encodingId),
			() -> assertThat(url.getOriginUrl()).isEqualTo(originUrl),
			() -> assertThat(url.getEncodedUrl()).hasSize(7)
		);
	}
}