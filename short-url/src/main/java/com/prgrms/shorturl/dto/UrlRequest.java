package com.prgrms.shorturl.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class UrlRequest {

    private String url;

    public UrlRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
