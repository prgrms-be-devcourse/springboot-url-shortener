package com.ryu.urlshortner.url.application.dto.response;

import com.ryu.urlshortner.url.domain.Url;

public class UrlDto {
    private final String originUrl;
    private final String shortUrl;

    private UrlDto(Builder builder) {
        this.originUrl = builder.originUrl;
        this.shortUrl = builder.shortUrl;
    }

    public static UrlDto from(Url url) {
        return UrlDto.builder()
                .originUrl(url.getOriginUrl())
                .shortUrl(url.getShortUrl())
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public static class Builder {
        private String originUrl;
        private String shortUrl;

        public Builder originUrl(String originUrl) {
            this.originUrl = originUrl;
            return this;
        }

        public Builder shortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
            return this;
        }

        public UrlDto build() {
            return new UrlDto(this);
        }
    }
}
