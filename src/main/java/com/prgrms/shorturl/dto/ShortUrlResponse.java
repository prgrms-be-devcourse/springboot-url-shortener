package com.prgrms.shorturl.dto;

import lombok.Builder;

public record ShortUrlResponse(String id, String originalUrl, String base62Url) {
    @Builder
    public ShortUrlResponse {
    }
}
