package com.programmers.urlshortener.url.dto.response;

import java.time.LocalDateTime;

import com.programmers.urlshortener.url.domain.Algorithm;
import com.programmers.urlshortener.url.domain.Url;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlResponse {

    private Algorithm algorithm;
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createdAt;
    private Long count;

    @Builder
    private UrlResponse(Algorithm algorithm, String originalUrl, String shortUrl, LocalDateTime createdAt, Long count) {
        this.algorithm = algorithm;
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.createdAt = createdAt;
        this.count = count;
    }

    public static UrlResponse from(Url url) {
        return UrlResponse.builder()
            .algorithm(url.getAlgorithm())
            .originalUrl(url.getOriginalUrl())
            .shortUrl(url.getShortUrl())
            .createdAt(url.getCreatedAt())
            .count(url.getCount())
            .build();
    }
}
