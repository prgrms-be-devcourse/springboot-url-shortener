package com.tangerine.urlshortener.controller;

import com.tangerine.urlshortener.model.UrlMapping;

public record UrlMappingResult(
        String originUrl,
        String shortUrl,
        long requestCount
) {

    public static UrlMappingResult of(UrlMapping entity) {
        return new UrlMappingResult(
                entity.getOriginUrlText(),
                entity.getShortUrlText(),
                entity.getRequestCountValue()
        );
    }

}
