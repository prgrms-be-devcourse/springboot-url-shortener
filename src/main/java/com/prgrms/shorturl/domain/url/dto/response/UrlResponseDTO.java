package com.prgrms.shorturl.domain.url.dto.response;

public record UrlResponseDTO(
        String originUrl,
        String shortUrl,
        Long requestCount
) {
}
