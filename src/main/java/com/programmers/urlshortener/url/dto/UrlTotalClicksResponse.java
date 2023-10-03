package com.programmers.urlshortener.url.dto;

public record UrlTotalClicksResponse(
	String shortenedUrl,
	int totalClicks
) {
	public static UrlTotalClicksResponse of(String shortenedUrl, int totalClicks) {
		return new UrlTotalClicksResponse(shortenedUrl, totalClicks);
	}
}
