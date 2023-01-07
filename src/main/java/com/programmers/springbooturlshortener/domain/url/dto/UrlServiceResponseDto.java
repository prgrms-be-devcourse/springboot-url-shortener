package com.programmers.springbooturlshortener.domain.url.dto;

public record UrlServiceResponseDto(String originUrl, String shortUrl, Long requestCount) {
}