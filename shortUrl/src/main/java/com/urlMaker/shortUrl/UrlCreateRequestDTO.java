package com.urlMaker.shortUrl;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record UrlCreateRequestDTO(
        @URL
        @NotBlank
        String originUrl,

        @NotBlank
        String algorithm
) {

    public Url toEntity() {
        return Url.builder()
                .originUrl(originUrl)
                .algorithm(algorithm)
                .build();
    }
}
