package com.blessing333.urlshortner.domain.model.url.shortener.impl;

import com.blessing333.urlshortner.domain.model.url.BasicUrlCreateCommand;
import com.blessing333.urlshortner.domain.model.url.UrlCreateCommand;
import com.blessing333.urlshortner.domain.model.url.key.KeyGenerator;
import com.blessing333.urlshortner.domain.model.url.shortener.ShortUrl;
import com.blessing333.urlshortner.domain.model.url.shortener.UrlShortener;

public class BasicUrlShortener extends AbstractUrlShortenerTemplate implements UrlShortener {

    public BasicUrlShortener(KeyGenerator keyGenerator) {
        super(keyGenerator);
    }

    @Override
    public ShortUrl shorteningUrl(UrlCreateCommand urlCommand) {
        return super.shorteningUrl(urlCommand);
    }

    @Override
    protected String createShortUrl(String key, UrlCreateCommand command) {
        return key;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(BasicUrlCreateCommand.class);
    }
}
