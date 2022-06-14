package com.blessing333.urlshortner.domain.model.url.shortener;

public class UrlShortenerNotFoundException extends RuntimeException {
    public UrlShortenerNotFoundException(String message) {
        super(message);
    }
}
