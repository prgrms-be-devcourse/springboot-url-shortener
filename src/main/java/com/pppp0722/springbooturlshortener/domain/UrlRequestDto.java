package com.pppp0722.springbooturlshortener.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UrlRequestDto {

    private String originalUrl;

    public Url toEntity() {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);

        return url;
    }
}
