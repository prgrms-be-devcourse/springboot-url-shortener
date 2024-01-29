package com.example.demo.dto;

public record UrlResponse(
        String url,
        String orgUrl
) {
    public static UrlResponse of(
            String url,
            String orgUrl
    ) {
        return new UrlResponse(url, orgUrl);
    }
}
