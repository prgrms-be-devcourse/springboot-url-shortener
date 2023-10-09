package com.seungwon.springbooturlshortener.domain.encoder;

public class Base62encoder implements Encoder {
	private static final int BASE = 62;
	private static final char[] CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

	@Override
	public String encode(long originalValue) {
		final StringBuilder encodedValue = new StringBuilder();

		do {
			int index = (int)originalValue % BASE;
			encodedValue.append(CHARSET[index]);
			originalValue /= BASE;
		} while (originalValue > 0);

		return encodedValue.toString();
	}
}
