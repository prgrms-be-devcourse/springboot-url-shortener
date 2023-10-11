package com.programmers.springbooturlshortener.url.presentation.response;

import com.programmers.springbooturlshortener.url.domain.Url;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UrlResponse {

	private Long urlId;
	private String originUrl;
	private String shortUrlKey;
	private long requestCount;

	public static UrlResponse from(Url url) {
		return UrlResponse.builder()
			.urlId(url.getUrlId())
			.originUrl(url.getOriginUrl())
			.shortUrlKey(url.getShortUrlKey())
			.requestCount(url.getRequestCount())
			.build();
	}
}
