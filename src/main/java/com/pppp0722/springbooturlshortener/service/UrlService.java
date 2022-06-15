package com.pppp0722.springbooturlshortener.service;

import com.pppp0722.springbooturlshortener.domain.UrlRequestDto;
import com.pppp0722.springbooturlshortener.domain.UrlResponseDto;

public interface UrlService {

    UrlResponseDto createUrl(UrlRequestDto urlRequestDto);

    String getOriginalUrl(String shortId);
}
