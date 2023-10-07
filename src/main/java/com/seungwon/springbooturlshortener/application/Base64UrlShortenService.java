package com.seungwon.springbooturlshortener.application;

import org.springframework.stereotype.Service;
import org.apache.commons.codec.binary.Base64;
import com.seungwon.springbooturlshortener.domain.Url;

@Service
public class Base64UrlShortenService implements UrlShortenService {
	@Override
	public String shorten(Url url) {
		String id = url.getId().toString();

		return new String(Base64.encodeBase64URLSafe(id.getBytes()));
	}
}
