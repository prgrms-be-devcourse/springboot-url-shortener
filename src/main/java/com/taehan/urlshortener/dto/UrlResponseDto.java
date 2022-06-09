package com.taehan.urlshortener.dto;

public class UrlResponseDto {
    String shortUrl;

    public UrlResponseDto(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
