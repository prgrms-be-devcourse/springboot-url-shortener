package com.urlMaker.shortUrl;

import lombok.Builder;

@Builder
public record UrlResponseDTO(
        String originUrl,
        Integer requestCount
) {
}
