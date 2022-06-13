package com.prgrms.urlshortener.dto;

import com.prgrms.urlshortener.domain.Url;

public class UrlResponse {

    private String originUrl;
    private String shortedUrl;
    private long requestCount;

    private UrlResponse() {
    }

    public UrlResponse(String originUrl, String shortedUrl, long requestCount) {
        this.originUrl = originUrl;
        this.shortedUrl = shortedUrl;
        this.requestCount = requestCount;
    }

    public static UrlResponse from(Url url) {
        return new UrlResponse(
            url.getOriginUrl(),
            url.getShortedUrl().getUrl(),
            url.getRequestCount()
        );
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getShortedUrl() {
        return shortedUrl;
    }

    public long getRequestCount() {
        return requestCount;
    }

}
