package com.blessing333.urlshortner.web;

import com.blessing333.urlshortner.domain.model.url.BasicUrlCreateCommand;
import com.blessing333.urlshortner.domain.model.url.CustomDomainUrlCreateCommand;
import com.blessing333.urlshortner.domain.model.url.UrlCreateCommand;
import com.blessing333.urlshortner.domain.model.url.UrlShorteningOption;

public class Converter {
    public UrlCreateCommand convert(UrlCreateRequest request) {
        UrlShorteningOption option = UrlShorteningOption.valueOf(request.getOption());
        switch (option) {
            case BASIC:
                return new BasicUrlCreateCommand(request.getOriginalUrl());
            case CUSTOM_DOMAIN:
                return new CustomDomainUrlCreateCommand(request.getOriginalUrl(), request.getCustomDomain());
            default:
                throw new IllegalArgumentException("invalid UrlCreateRequestType");
        }
    }
}
