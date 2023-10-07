package com.seungwon.springbooturlshortener.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.seungwon.springbooturlshortener.domain.Url;

@Service
public class RandomUrlShortenService implements UrlShortenService {

	@Override
	public String shorten(Url original) {
		String shortenedValue = UUID.randomUUID()
			.toString()
			.replace("-", "");

		return shortenedValue;
	}
}
