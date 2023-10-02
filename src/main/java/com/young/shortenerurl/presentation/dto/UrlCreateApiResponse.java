package com.young.shortenerurl.presentation.dto;

public record UrlCreateApiResponse(String encodedUrl) {

    public static UrlCreateApiResponse from(String encodedUrl){
        return new UrlCreateApiResponse(encodedUrl);
    }
}
