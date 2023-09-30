package com.urlMaker.dto;

import lombok.Builder;

@Builder
public record UrlResponseDTO(
        String originUrl,
        Integer requestCount
) {
}
