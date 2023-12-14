package com.example.shorturl.dto.response;

public record OriginUrlResponse(
    String originUrl
) {
    public static OriginUrlResponse toDto(String originUrl) {
        return new OriginUrlResponse(originUrl);
    }
}
