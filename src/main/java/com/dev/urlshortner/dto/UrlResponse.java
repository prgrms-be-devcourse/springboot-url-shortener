package com.dev.urlshortner.dto;

public record UrlResponse(String shortUrl, String originalUrl) {
	public static UrlResponse of(String shortUrl, String originalUrl) {
		return new UrlResponse(shortUrl, originalUrl);
	}
}
