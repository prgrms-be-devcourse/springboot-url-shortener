package com.prgrms.shorturl.dto;

import lombok.Builder;

public record ShortUrlRequest(String originalUrl) {
        @Builder
        public ShortUrlRequest {
        }
}
