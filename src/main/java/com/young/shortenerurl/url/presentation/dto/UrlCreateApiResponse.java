package com.young.shortenerurl.url.presentation.dto;

public record UrlCreateApiResponse(String encodedUrl) {

    public static UrlCreateApiResponse from(String encodedUrl) {
        return new UrlCreateApiResponse(encodedUrl);
    }
}
