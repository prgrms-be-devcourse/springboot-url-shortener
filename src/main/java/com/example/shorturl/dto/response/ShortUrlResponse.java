package com.example.shorturl.dto.response;

import com.example.shorturl.domain.Url;
import com.example.shorturl.util.formatter.UrlFormatter;

public record ShortUrlResponse(
    String shortUrl,
    int requestCount
) {
    public static ShortUrlResponse toDto(Url url) {
        return new ShortUrlResponse(
            UrlFormatter.format(url.getShortUrl()),
            url.getRequestCount()
        );
    }
}
