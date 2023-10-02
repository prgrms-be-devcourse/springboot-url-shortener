package com.programmers.urlshortener.url.dto;

public record UrlShortenResponse(
	String originalUrl,
	String shortenedUrl
) {
	public static UrlShortenResponse of(String originalUrl, String shortenedUrl) {
		return new UrlShortenResponse(originalUrl, shortenedUrl);
	}
}
