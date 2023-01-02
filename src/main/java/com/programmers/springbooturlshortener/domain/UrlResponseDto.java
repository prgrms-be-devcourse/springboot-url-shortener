package com.programmers.springbooturlshortener.domain;

public record UrlResponseDto(String originUrl, String shortUrl, Long requestCount) {
}
