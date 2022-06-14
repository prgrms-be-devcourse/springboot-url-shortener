package com.blessing333.urlshortner.domain.application;

import com.blessing333.urlshortner.domain.model.url.Url;
import com.blessing333.urlshortner.domain.model.url.UrlCreateCommand;

public interface UrlService {
    String registerShortenUrl(UrlCreateCommand command);

    Url loadUrlById(String urlId);

    Url loadUrlForRedirect(String urlId);
}
