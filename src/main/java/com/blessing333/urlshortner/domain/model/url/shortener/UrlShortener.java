package com.blessing333.urlshortner.domain.model.url.shortener;

import com.blessing333.urlshortner.domain.model.url.UrlCreateCommand;

public interface UrlShortener {
    ShortUrl shorteningUrl(UrlCreateCommand urlCommand);

    boolean supports(Class<?> clazz);
}
