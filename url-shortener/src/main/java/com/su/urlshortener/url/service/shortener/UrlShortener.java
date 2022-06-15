package com.su.urlshortener.url.service.shortener;

@FunctionalInterface
public interface UrlShortener {
    String makeShorteningKey(String originUrl);
}
