package com.pppp0722.springbooturlshortener.service.url;

import com.pppp0722.springbooturlshortener.domain.url.UrlRequestDto;
import com.pppp0722.springbooturlshortener.domain.url.UrlResponseDto;

public interface UrlService {

    UrlResponseDto createUrl(UrlRequestDto urlRequestDto);

    String getOriginalUrl(String shortId);
}
