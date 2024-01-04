package com.programmers.urlshortener.domain.url.application.dto.response;

import java.time.LocalDateTime;

import com.programmers.urlshortener.domain.url.domain.Url;

public record ShortUrlResponse(
	Long id,
	String originalUrl,
	String shortUrl,
	int requestCount,
	String encoderType,
	LocalDateTime createdAt
) {

	public static ShortUrlResponse from(Url url) {

		return new ShortUrlResponse(url.getId(), url.getOriginalUrl(), url.getShortUrl(), url.getRequestCount(), url.getEncoderType().name(), url.getCreatedAt());
	}
}
