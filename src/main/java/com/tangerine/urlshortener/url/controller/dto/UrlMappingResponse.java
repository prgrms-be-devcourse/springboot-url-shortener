package com.tangerine.urlshortener.url.controller.dto;

import com.tangerine.urlshortener.url.service.dto.UrlMappingResult;

public record UrlMappingResponse(
        Long id,
        String originUrl,
        String shortUrl,
        String algorithm,
        long requestCount
) {

    public static UrlMappingResponse of(UrlMappingResult result) {
        return new UrlMappingResponse(
                result.id(),
                result.originUrl().getOriginUrlText(),
                result.shortUrl().getShortUrlText(),
                result.algorithm().name(),
                result.requestCount().getRequestCount()
        );
    }

}
