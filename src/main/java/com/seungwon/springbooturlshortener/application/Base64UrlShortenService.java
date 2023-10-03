package com.seungwon.springbooturlshortener.application;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.seungwon.springbooturlshortener.domain.Url;

@Service
public class Base64UrlShortenService implements UrlShortenService {

	private static final String PADDING = "=";
	private static final int KEY_LENGTH = 7;

	@Override
	public String shorten(Url url) {
		String shortUrlKey = Base64.getUrlEncoder()
			.encodeToString(url.getId().toString().getBytes());

		int paddingCount = KEY_LENGTH - shortUrlKey.length();
		shortUrlKey += PADDING.repeat(paddingCount);

		return shortUrlKey;
	}
}
