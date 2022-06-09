package com.prgrms.shortener.domain;

public interface UrlShorteningStrategy {

  String shorten(Long id);
}
