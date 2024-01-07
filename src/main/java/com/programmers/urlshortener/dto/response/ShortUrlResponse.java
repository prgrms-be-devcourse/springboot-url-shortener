package com.programmers.urlshortener.dto.response;

import com.programmers.urlshortener.domain.ShortUrl;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

public record ShortUrlResponse(String shortUrl, String originUrl, LocalDateTime createdAt) {
    public static ShortUrlResponse from(ShortUrl shortUrl) {
        String location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/{urlKey}")
                .buildAndExpand(shortUrl.getUrlKey())
                .toUriString();


        return new ShortUrlResponse(location, shortUrl.getOriginUrl(), shortUrl.getCreatedAt());
    }
}
