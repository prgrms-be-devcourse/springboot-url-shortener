package com.programmers.urlshortener.url.dto;

public record UrlShortenResponse(
	String originalUrl,
	String shortenedUrl,
	String shorteningKey
) {
	public static UrlShortenResponse of(String originalUrl, String shortenedUrl, String shorteningKey) {
		return new UrlShortenResponse(originalUrl, shortenedUrl, shorteningKey);
	}
}
