package com.progms.shorturl.dto;

import com.progms.shorturl.entity.ShortUrl;
import jakarta.validation.constraints.NotBlank;

public record UrlRequest(
        @NotBlank String url
) {

    public ShortUrl toEntity() {
        return ShortUrl.createShortUrl(url);
    }
}
