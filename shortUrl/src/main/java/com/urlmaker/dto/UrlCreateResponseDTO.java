package com.urlmaker.dto;

public record UrlCreateResponseDTO(
        String shortenUrl,
        Integer requestCount
) {
}
