package com.programmers.urlshortener.algorithm;

import org.springframework.stereotype.Component;

@Component
public class Base62 implements Algorithm<Long, String> {

	private static final String CODEC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	@Override
	public String encode(Long id) {
		final StringBuilder stringBuilder = new StringBuilder();
		while (id > 0) {
			stringBuilder.append(CODEC.charAt((int)(id % CODEC.length())));
			id /= CODEC.length();
		}

		return stringBuilder.toString();
	}

	@Override
	public Long decode(final String shorteningKey) {
		long decodedId = 0;
		long multiple = 1;
		for (int i = 0; i < shorteningKey.length(); i++) {
			decodedId += CODEC.indexOf(shorteningKey.charAt(i)) * multiple;
			multiple += CODEC.length();
		}

		return decodedId;
	}
}
