package com.blessing333.urlshortner.domain.model.url;


public interface UrlCreateCommand {
    Long getUrlSequence();

    void setUrlSequence(Long id);

    String getOriginalUrl();
}
