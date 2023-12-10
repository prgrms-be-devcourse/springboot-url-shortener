package com.dev.shortenerurl.supports.fixture;

import com.dev.shortenerurl.url.domain.model.Url;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UrlFixture {

	private static final String ORIGIN_URL = "http://localhost:8080/originUrl";
	private static final Long ENCODING_ID = 100L;
	private static final String BASE_62 = "BASE_62";

	public static Url getUrl() {
		return new Url(ORIGIN_URL, ENCODING_ID, BASE_62);
	}
}
