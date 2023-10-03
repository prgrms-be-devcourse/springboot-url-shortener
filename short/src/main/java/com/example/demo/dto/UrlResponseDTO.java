package com.example.demo.dto;

public record UrlResponseDTO(
        String url,
        String orgUrl
) {
    public static UrlResponseDTO of(
            String url,
            String orgUrl
    ) {
        return new UrlResponseDTO(url, orgUrl);
    }
}
