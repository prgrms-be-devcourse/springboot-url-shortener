package com.blessing333.urlshortner.domain.model.url;

public class BasicUrlCreateCommand implements UrlCreateCommand {
    private final String originalUrl;
    private Long urlSequence;

    public BasicUrlCreateCommand(String originalUrl) {
        this.originalUrl = originalUrl;
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
