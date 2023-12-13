package com.prgrms.shorturl.dto;

import com.prgrms.shorturl.domain.Url;
import lombok.Builder;

public record UrlResponse(Long id, String hash, String originalUrl, String shortUrl, int count) {
    @Builder
    public UrlResponse {
    }

    public static UrlResponse toShortUrlResponse(Url url, String protocol) {
        return UrlResponse.builder()
                .id(url.getId())
                .hash(url.getHash())
                .originalUrl(protocol + url.getOriginalUrl())
                .shortUrl(url.getShortUrl())
                .count(url.getCount())
                .build();
    }
}
