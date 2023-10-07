package com.seungwon.springbooturlshortener.application;

import com.seungwon.springbooturlshortener.domain.Url;

public interface UrlShortenService {

	String shorten(Url original);

	public static UrlShortenService from(String strategy) {
		if (strategy == null) {
			return new Base64UrlShortenService();
		}
		switch (strategy.toLowerCase()) {
			case "random" -> {
				return new RandomUrlShortenService();
			}
			case "base64" -> {
				return new Base64UrlShortenService();
			}
			default -> {
				return new Base64UrlShortenService();
			}
		}
	}
}
