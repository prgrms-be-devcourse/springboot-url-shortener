package com.urlmaker.dto;

import lombok.Builder;

@Builder
public record UrlCreateResponseDTO(
        String shortenUrl,
        Integer requestCount
) {
}
