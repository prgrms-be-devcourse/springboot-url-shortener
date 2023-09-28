package com.prgrms.urlshortener.service;

public interface UrlService {

    String createShortenUrl(String originUrl, String strategyType);
}