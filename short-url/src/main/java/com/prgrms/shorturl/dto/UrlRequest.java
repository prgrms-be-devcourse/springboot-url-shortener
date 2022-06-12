package com.prgrms.shorturl.dto;

import com.prgrms.shorturl.domain.Url;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlRequest {

    private String url;

    public UrlRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
