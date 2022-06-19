package com.waterfogsw.springbooturlshortener.url.serivce;

import com.waterfogsw.springbooturlshortener.url.entity.HashType;

public interface UrlService {

  String shorten(String url, HashType hashType);

  String getRedirectUrl(String shortKey);
}
