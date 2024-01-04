package com.programmers.urlshortener.domain.encoder.domain;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder implements UrlEncoder {

	private final int BASE62_SIZE = 62;
	private final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	@Override
	public String encode(long value) {
		StringBuffer buffer = new StringBuffer();

		while(value > 0) {
			buffer.append(BASE62_CHAR.charAt((int) (value % BASE62_SIZE)));
			value /= BASE62_SIZE;
		}
		return buffer.toString();
	}
}
