package com.urlmaker.dto;

import lombok.Builder;

@Builder
public record UrlGetResponseDTO(
        String originUrl,
        Integer requestCount
) {
}
