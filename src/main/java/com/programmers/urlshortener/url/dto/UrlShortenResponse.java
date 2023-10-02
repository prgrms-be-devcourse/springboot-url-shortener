package com.programmers.urlshortener.url.dto;

public record UrlShortenResponse(
	String originalUrl,
	String shortenedUrl
) {
}
