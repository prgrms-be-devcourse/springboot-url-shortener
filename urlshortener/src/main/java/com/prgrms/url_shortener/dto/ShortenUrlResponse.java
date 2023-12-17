package com.prgrms.url_shortener.dto;

public record ShortenUrlResponse(
    String shortenUrl,
    int requestCount
){}
