package com.prgrmrs.urlshortener.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Embeddable
public class OriginalUrl {

    @NotBlank
    @Pattern(regexp = "^(http://|https://).+")
    @Column(name = "original_url", nullable = false)
    private String url;

    public OriginalUrl(String url) {
        this.url = url;
    }

    public OriginalUrl() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
