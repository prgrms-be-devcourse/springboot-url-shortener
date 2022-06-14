package com.blessing333.urlshortner.domain.model.url.shortener.impl;

import com.blessing333.urlshortner.domain.model.url.CustomDomainUrlCreateCommand;
import com.blessing333.urlshortner.domain.model.url.UrlCreateCommand;
import com.blessing333.urlshortner.domain.model.url.key.KeyGenerator;
import com.blessing333.urlshortner.domain.model.url.shortener.ShortUrl;
import com.blessing333.urlshortner.domain.model.url.shortener.UrlShortener;
import lombok.Getter;

@Getter
public class CustomDomainUrlShortener extends AbstractUrlShortenerTemplate implements UrlShortener {

    public CustomDomainUrlShortener(KeyGenerator keyGenerator) {
        super(keyGenerator);
    }

    @Override
    public ShortUrl shorteningUrl(UrlCreateCommand command) {
        return super.shorteningUrl(command);
    }

    @Override
    protected String createShortUrl(String key, UrlCreateCommand urlCreateCommand) {
        CustomDomainUrlCreateCommand command = (CustomDomainUrlCreateCommand) urlCreateCommand;
        return command.getCustomDomain() + "/" + key;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(CustomDomainUrlCreateCommand.class);
    }
}
