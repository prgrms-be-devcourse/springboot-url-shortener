package com.waterfogsw.springbooturlshortener.url.serivce;

import com.waterfogsw.springbooturlshortener.url.entity.HashType;

public interface UrlService {

  void shorten(String url, HashType hashType);
}
