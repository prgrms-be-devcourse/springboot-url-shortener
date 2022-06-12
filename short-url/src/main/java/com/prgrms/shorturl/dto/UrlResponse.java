package com.prgrms.shorturl.dto;

public class UrlResponse {

    private String shortenUrl;

    public UrlResponse(String shortenUrl) {
        this.shortenUrl = shortenUrl;
    }

    public String getShortenUrl() {
        return shortenUrl;
    }
}
