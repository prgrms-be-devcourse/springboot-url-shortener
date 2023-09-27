package com.urlMaker.shortUrl;

public record UrlCreateRequestDTO(
        String originUrl,
        String algorithm
) {

    public Url toEntity() {
        return Url.builder()
                .originUrl(originUrl)
                .algorithm(algorithm)
                .build();
    }
}
