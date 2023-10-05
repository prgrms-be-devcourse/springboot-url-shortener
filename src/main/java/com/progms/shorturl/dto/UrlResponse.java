package com.progms.shorturl.dto;

import com.progms.shorturl.entity.ShortUrl;
import lombok.Builder;

@Builder
public record UrlResponse(
        String shortUrl
) {

    public static UrlResponse from(ShortUrl shortUrl) {
        return UrlResponse.builder()
                .shortUrl(shortUrl.getShortenUrl())
                .build();
    }
}

