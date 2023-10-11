package com.prgrmrs.urlshortener.presentation.dto;

import com.prgrmrs.urlshortener.model.UrlMapping;

public record ShortenUrlResponse(String hash, String originalUrl) {

    public static ShortenUrlResponse to(UrlMapping urlMapping) {
        return new ShortenUrlResponse(urlMapping.getHash(), urlMapping.getOriginalUrl().getUrl());
    }
}
