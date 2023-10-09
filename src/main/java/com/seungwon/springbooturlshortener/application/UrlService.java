package com.seungwon.springbooturlshortener.application;

import org.springframework.stereotype.Service;

import com.seungwon.springbooturlshortener.domain.EncoderType;
import com.seungwon.springbooturlshortener.domain.Url;
import com.seungwon.springbooturlshortener.domain.encoder.Encoder;

@Service
public class UrlService {

	public void shorten(Url url, String type) {
		Encoder encoder = EncoderType.getEncoder(type);

		long shortenCriteria = url.getId();
		String shortUrlKey = encoder.encode(shortenCriteria);

		url.saveShortUrlKey(shortUrlKey);
	}
}
