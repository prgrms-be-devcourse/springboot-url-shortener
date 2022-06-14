package com.blessing333.urlshortner.domain.model.url;

import lombok.Getter;

@Getter
public class CustomDomainUrlCreateCommand implements UrlCreateCommand {
    private final String originalUrl;
    private final String customDomain;
    private Long urlSequence;

    public CustomDomainUrlCreateCommand(String originalUrl, String customDomain) {
        this.originalUrl = originalUrl;
        this.customDomain = customDomain;
    }

    @Override
    public Long getUrlSequence() {
        return urlSequence;
    }

    @Override
    public void setUrlSequence(Long urlSequence) {
        this.urlSequence = urlSequence;
    }

    @Override
    public String getOriginalUrl() {
        return originalUrl;
    }
}

