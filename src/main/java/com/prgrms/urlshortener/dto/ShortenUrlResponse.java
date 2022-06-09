package com.prgrms.urlshortener.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ShortenUrlResponse extends UrlResponse{
    private String encodedId;
    private long requestCount;
    private LocalDateTime createdAt;

    public ShortenUrlResponse(String encodedId, String url, long requestCount, LocalDateTime createdAt) {
        super(url);
        this.encodedId = encodedId;
        this.requestCount = requestCount;
        this.createdAt = createdAt;
    }
}
