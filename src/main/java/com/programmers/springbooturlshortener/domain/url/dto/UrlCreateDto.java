package com.programmers.springbooturlshortener.domain.url.dto;

import com.programmers.springbooturlshortener.domain.url.Url;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public record UrlCreateDto(@NotBlank @URL @Column(nullable = false) String originUrl,
                           @NotBlank @Column(nullable = false) String algorithm) {

    public Url toEntity() {
        return Url.builder()
                .originUrl(originUrl)
                .algorithm(algorithm)
                .requestCount(1L)
                .build();
    }
}