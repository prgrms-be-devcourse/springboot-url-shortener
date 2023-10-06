package com.prgrms.urlshortener.utils;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Base62Util {

	private static final int BASE62 = 62;
	private static final String BASE62_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public static String encode(int value) {
		final StringBuilder sb = new StringBuilder();

		do {
			sb.append(BASE62_CHAR.charAt(value % BASE62));
			value /= BASE62;
		} while (value > 0);

		return sb.toString();
	}

	public static int decode(String value) {
		int result = 0;
		int power = 1;

		for (int i = 0; i < value.length(); i++) {
			result += BASE62_CHAR.indexOf(value.charAt(i)) * power;
			power *= BASE62;
		}

		return result;
	}
}
