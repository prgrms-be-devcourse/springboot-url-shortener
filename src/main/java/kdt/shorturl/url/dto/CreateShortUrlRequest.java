package kdt.shorturl.url.dto;

import kdt.shorturl.url.domain.Algorithm;
import kdt.shorturl.url.domain.Url;

public record CreateShortUrlRequest(

        String originUrl,
        Algorithm algorithm
) {
    public Url toEntity() {
        return Url.builder()
                .originUrl(originUrl)
                .algorithm(algorithm)
                .build();
    }
}
