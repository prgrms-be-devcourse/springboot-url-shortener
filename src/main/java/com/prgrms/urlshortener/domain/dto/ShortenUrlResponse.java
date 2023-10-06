package com.prgrms.urlshortener.domain.dto;

public record ShortenUrlResponse(
	String originalUrl,
	String shortUrl
) {
}
