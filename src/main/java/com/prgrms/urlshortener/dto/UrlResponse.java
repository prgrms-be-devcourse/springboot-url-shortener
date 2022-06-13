package com.prgrms.urlshortener.dto;

import lombok.Getter;

@Getter
public class UrlResponse {
    private String url;

    public UrlResponse(String url) {
        this.url = url;
    }
}
