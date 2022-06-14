package com.blessing333.urlshortner.domain.model.url.shortener.impl;

import com.blessing333.urlshortner.domain.model.url.UrlCreateCommand;
import com.blessing333.urlshortner.domain.model.url.key.KeyGenerator;
import com.blessing333.urlshortner.domain.model.url.shortener.ShortUrl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractUrlShortenerTemplate {
    private static final String HOST = "http://localhost:8080/";
    private final KeyGenerator keyGenerator;

    protected ShortUrl shorteningUrl(UrlCreateCommand urlCommand) {
        String key = keyGenerator.generateKey(urlCommand.getUrlSequence());
        String shortUrl = createShortUrl(key, urlCommand);
        return new ShortUrl(key, HOST + shortUrl);
    }

    protected abstract String createShortUrl(String key, UrlCreateCommand command);
}
