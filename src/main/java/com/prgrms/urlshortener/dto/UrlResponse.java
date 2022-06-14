package com.prgrms.urlshortener.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prgrms.urlshortener.domain.Url;

public class UrlResponse {

    private String originUrl;
    private String shortedUrl;
    private long requestCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDateTime;

    public UrlResponse(String originUrl, String shortedUrl, long requestCount, LocalDateTime createdDateTime) {
        this.originUrl = originUrl;
        this.shortedUrl = shortedUrl;
        this.requestCount = requestCount;
        this.createdDateTime = createdDateTime;
    }

    public static UrlResponse from(Url url) {
        return new UrlResponse(
            url.getOriginUrl(),
            url.getShortedUrl().getUrl(),
            url.getRequestCount(),
            url.getCreatedDateTime()
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }
}
