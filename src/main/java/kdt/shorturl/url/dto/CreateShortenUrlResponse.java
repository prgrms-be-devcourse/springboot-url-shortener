package kdt.shorturl.url.dto;

import kdt.shorturl.url.domain.Algorithm;
import kdt.shorturl.url.domain.Url;

public record CreateShortenUrlResponse(
        Long id, String shortenUrl, String originUrl, Algorithm algorithm, Long viewCount, boolean isNew
) {
    public static CreateShortenUrlResponse from(Url url, boolean isNew) {
        return new CreateShortenUrlResponse(
                url.getId(),
                url.getShortUrl(),
                url.getOriginUrl(),
                url.getAlgorithm(),
                url.getViewCount(),
                isNew
        );
    }
}
