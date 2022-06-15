package com.pppp0722.springbooturlshortener.domain.url;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UrlResponseDto {

    private String originalUrl;

    private String shortUrl;
}
