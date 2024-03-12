package com.programmers.urlshortener.url.dto;

public record UrlTotalClicksResponse(
	String shortenedUrl,
	int totalClicks
) {
}
