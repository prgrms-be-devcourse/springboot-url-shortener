package com.ryu.urlshortner.url.presentation.dto.response;

import com.ryu.urlshortner.url.application.dto.response.UrlDto;

public class UrlResponse {
    private final String originUrl;
    private final String shortUrl;

    private UrlResponse(Builder builder) {
        this.originUrl = builder.originUrl;
        this.shortUrl = builder.shortUrl;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static UrlResponse from(UrlDto urlResponseDto) {
        return UrlResponse.builder()
                .originUrl(urlResponseDto.getOriginUrl())
                .shortUrl(urlResponseDto.getShortUrl())
                .build();
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

        public UrlResponse build() {
            return new UrlResponse(this);
        }
    }
}
