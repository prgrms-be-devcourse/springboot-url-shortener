package com.prgrms.urlshortener.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class Base62Util {

	private static final int BASE62 = 62;
	private static final String BASE62_CHAR = "aZbYc0XdWeV1fUgTh2SiRjQ3kPlOm4NnMoL5pKqJr6IsHtG7uFvEw8DxCyB9zA";
	private static final String PREFIX = "localhost:8080/";

	public static String encode(Long value) {
		final StringBuilder sb = new StringBuilder();
		sb.append(PREFIX);

		do {
			sb.append(BASE62_CHAR.charAt((int)(value % BASE62)));
			value /= BASE62;
		} while (value > 0);

		return sb.toString();
	}

	public static Long decode(String value) {
		long result = 0L;
		int power = 1;

		for (int i = 0; i < value.length(); i++) {
			result += (long)BASE62_CHAR.indexOf(value.charAt(i)) * power;
			power *= BASE62;
		}

		return result;
	}
}
