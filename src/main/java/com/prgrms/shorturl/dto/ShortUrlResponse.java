package com.prgrms.shorturl.dto;

import com.prgrms.shorturl.domain.ShortUrl;
import lombok.Builder;

public record ShortUrlResponse(String id, String originalUrl, String base62Url) {
    @Builder
    public ShortUrlResponse {
    }

    public static ShortUrlResponse toShortUrlResponse(ShortUrl shortUrl, String protocol) {
        return ShortUrlResponse.builder()
                .id(shortUrl.getId())
                .originalUrl(protocol + shortUrl.getOriginalUrl())
                .base62Url(shortUrl.getBase62Url())
                .build();
    }
}
