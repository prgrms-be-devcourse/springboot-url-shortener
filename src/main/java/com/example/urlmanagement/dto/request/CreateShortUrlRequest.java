package com.example.urlmanagement.dto.request;

import com.example.urlmanagement.domain.Url;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateShortUrlRequest {

    private final String originalUrl;
    private final String encodeType;

    @Builder
    public CreateShortUrlRequest(String originalUrl, String encodeType) {
        this.originalUrl = originalUrl;
        this.encodeType = encodeType;
    }

    public Url toUrl() {
        return Url.builder()
                .originalUrl(originalUrl)
                .build();
    }
}
