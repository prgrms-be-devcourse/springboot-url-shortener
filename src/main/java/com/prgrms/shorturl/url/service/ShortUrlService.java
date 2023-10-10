package com.prgrms.shorturl.url.service;

public interface ShortUrlService {

    String creatShortUrl(String originUrl, String strategyType);

    String getOriginUrl(String shortUrl);
}
