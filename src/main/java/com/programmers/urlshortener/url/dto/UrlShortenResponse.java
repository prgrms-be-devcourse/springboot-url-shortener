package com.programmers.urlshortener.url.dto;

public record UrlShortenResponse(
	String originalUrl,
	String shortenedUrl,
	String shorteningKey
) {
	public static UrlShortenResponse of(
		final String originalUrl,
		final String shortenedUrl,
		final String shorteningKey
	) {
		return new UrlShortenResponse(originalUrl, shortenedUrl, shorteningKey);
	}
}
