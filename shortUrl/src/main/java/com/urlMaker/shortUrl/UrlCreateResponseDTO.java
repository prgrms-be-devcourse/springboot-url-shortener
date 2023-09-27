package com.urlMaker.shortUrl;

import lombok.Builder;

@Builder
public record UrlCreateResponseDTO(
        String shortenUrl,
        Integer requestCount
) {
}
