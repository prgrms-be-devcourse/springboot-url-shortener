package com.programmers.springbooturlshortener.domain.url.dto;

public record UrlResponseDto(String originUrl, String shortUrl, Long requestCount) {
}
