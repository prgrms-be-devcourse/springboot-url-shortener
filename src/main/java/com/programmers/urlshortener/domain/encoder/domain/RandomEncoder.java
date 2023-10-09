package com.programmers.urlshortener.domain.encoder.domain;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomEncoder implements UrlEncoder {

	private static final int DEFAULT_SIZE = 8;
	private final char[] RANDOM_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	private final Random random = new Random();

	@Override
	public String encode(long value) {
		StringBuilder encodedString = new StringBuilder(DEFAULT_SIZE);

		for (int i = 0; i < DEFAULT_SIZE; i++) {
			int randomNumber = random.nextInt(RANDOM_CHAR.length);
			int index = (int) ((randomNumber + value) % RANDOM_CHAR.length);

			encodedString.append(RANDOM_CHAR[index]);
		}

		return encodedString.toString();
	}
}
