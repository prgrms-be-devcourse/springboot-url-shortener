package com.blessing333.urlshortner.domain.model.url.shortener;

import com.blessing333.urlshortner.domain.model.url.UrlCreateCommand;

public interface UrlShorteningManager {
    ShortUrl shorteningUrl(UrlCreateCommand urlCommand);
}
