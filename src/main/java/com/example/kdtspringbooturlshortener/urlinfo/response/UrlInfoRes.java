package com.example.kdtspringbooturlshortener.urlinfo.response;

import com.example.kdtspringbooturlshortener.urlinfo.domain.UrlInfo;

public record UrlInfoRes(
        String originalUrl,
        String shortUrl
) {

    public static UrlInfoRes toDto(UrlInfo urlInfo) {
        return new UrlInfoRes(urlInfo.getOriginalUrl(), urlInfo.getShortUrl());
    }
}
