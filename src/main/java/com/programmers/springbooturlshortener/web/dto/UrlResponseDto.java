package com.programmers.springbooturlshortener.web.dto;

public record UrlResponseDto(String originUrl, String shortUrl, Long requestCount) {
}
