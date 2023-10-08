package com.seungwon.springbooturlshortener.application;

import com.seungwon.springbooturlshortener.exception.InvalidStrategyException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShortenServiceGenerator {
	public static UrlShortenService from(String strategy) {
		switch (strategy) {
			case "random" -> {
				return new RandomUrlShortenService();
			}
			case "base64" -> {
				return new Base64UrlShortenService();
			}
			default -> throw new InvalidStrategyException(strategy);
		}
	}
}
