package com.seungwon.springbooturlshortener;

import com.seungwon.springbooturlshortener.application.Base64UrlShortenService;
import com.seungwon.springbooturlshortener.application.RandomUrlShortenService;
import com.seungwon.springbooturlshortener.application.UrlShortenService;

public class ShortenServiceGenerator {
	public static UrlShortenService from(String strategy) {
		switch (strategy) {
			case "random" -> {
				return new RandomUrlShortenService();
			}
			default -> {
				return new Base64UrlShortenService();
			}
		}
	}
}
