package com.prgrms.shorturl.dto;

import lombok.Builder;

public record ShortUrlResponse(Long id, String originalUrl, String base62Url) {
    @Builder
    public ShortUrlResponse {
    }
}
