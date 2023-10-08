package com.tangerine.urlshortener.url.service.dto;

import com.tangerine.urlshortener.url.model.UrlMapping;
import com.tangerine.urlshortener.url.model.vo.Algorithm;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.model.vo.RequestCount;
import com.tangerine.urlshortener.url.model.vo.ShortUrl;

public record UrlMappingResult(
        Long id,
        OriginUrl originUrl,
        ShortUrl shortUrl,
        Algorithm algorithm,
        RequestCount requestCount
) {

    public static UrlMappingResult of(UrlMapping entity) {
        return new UrlMappingResult(
                entity.getId(),
                entity.getOriginUrl(),
                entity.getShortUrl(),
                entity.getAlgorithmName(),
                entity.getRequestCount()
        );
    }

    public String originUrlText() {
        return originUrl.getOriginUrlText();
    }

    public String shortUrlText() {
        return shortUrl.getShortUrlText();
    }

    public String algorithmName() {
        return algorithm.name();
    }

    public long requestCountValue() {
        return requestCount.getRequestCount();
    }
}
