package com.urlmaker.dto;

import com.urlmaker.url.Url;

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
