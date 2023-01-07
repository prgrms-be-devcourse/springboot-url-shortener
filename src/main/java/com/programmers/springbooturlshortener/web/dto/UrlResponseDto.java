package com.programmers.springbooturlshortener.web.dto;

import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceResponseDto;

public record UrlResponseDto(String originUrl, String shortUrl, Long requestCount) {

	private static final String DOMAIN = "localhost:8080/";

	public static UrlResponseDto toUrlResponseDto(UrlServiceResponseDto urlServiceResponseDto) {
		return new UrlResponseDto(
			urlServiceResponseDto.originUrl(),
			DOMAIN + urlServiceResponseDto.shortUrl(),
			urlServiceResponseDto.requestCount()
		);
	}
}
