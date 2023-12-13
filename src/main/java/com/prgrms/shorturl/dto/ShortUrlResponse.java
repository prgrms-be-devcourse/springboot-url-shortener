package com.prgrms.shorturl.dto;

import com.prgrms.shorturl.domain.Url;
import lombok.Builder;

public record ShortUrlResponse(String id, String originalUrl, String shortUrl, int count) {
    @Builder
    public ShortUrlResponse {
    }

    public static ShortUrlResponse toShortUrlResponse(Url url, String protocol) {
        return ShortUrlResponse.builder()
                .id(url.getId())
                .originalUrl(protocol + url.getOriginalUrl())
                .shortUrl(url.getShortUrl())
                .count(url.getCount())
                .build();
    }
}
