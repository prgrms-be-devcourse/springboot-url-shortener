package com.seungwon.springbooturlshortener.application.dto;

import com.seungwon.springbooturlshortener.domain.Url;

public record UrlCreateResponse(
	String urlKey
) {
	public static UrlCreateResponse from(Url url) {
		return new UrlCreateResponse(url.getShortUrlKey());
	}
}
