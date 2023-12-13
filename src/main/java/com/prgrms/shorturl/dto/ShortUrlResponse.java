package com.prgrms.shorturl.dto;

import com.prgrms.shorturl.domain.Url;
import com.prgrms.shorturl.utils.HashAlgorithm;
import lombok.Builder;

public record ShortUrlResponse(Long id, String hash, String originalUrl, String shortUrl, int count) {
    @Builder
    public ShortUrlResponse {
    }

    public static ShortUrlResponse toShortUrlResponse(Url url, String protocol) {
        return ShortUrlResponse.builder()
                .id(url.getId())
                .hash(url.getHash())
                .originalUrl(protocol + url.getOriginalUrl())
                .shortUrl(url.getShortUrl())
                .count(url.getCount())
                .build();
    }
}
