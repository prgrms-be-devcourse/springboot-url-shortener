package com.example.urlmanagement.dto.request;

import com.example.urlmanagement.domain.Url;
import lombok.Getter;

@Getter
public class CreateShortUrlRequest {

    private String originalUrl;
    private String encodeType;

    public Url toUrl() {
        return Url.builder()
                .originalUrl(originalUrl)
                .build();
    }
}
