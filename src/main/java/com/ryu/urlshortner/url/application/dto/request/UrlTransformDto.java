package com.ryu.urlshortner.url.application.dto.request;

import com.ryu.urlshortner.url.domain.Url;

import java.time.LocalDateTime;

public class UrlTransformDto {
    private String originUrl;
    private LocalDateTime expiredAt;

    private UrlTransformDto(Builder builder) {
        this.originUrl = builder.originUrl;
        this.expiredAt = builder.expiredAt;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public Url toEntity() {
        return Url.builder()
                .originUrl(originUrl)
                .expiredAt(expiredAt)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String originUrl;
        private LocalDateTime expiredAt;

        public Builder originUrl(String originUrl) {
            this.originUrl = originUrl;
            return this;
        }

        public Builder expiredAt(LocalDateTime expiredAt) {
            this.expiredAt = expiredAt;
            return this;
        }

        public UrlTransformDto build() {
            return new UrlTransformDto(this);
        }
    }
}
