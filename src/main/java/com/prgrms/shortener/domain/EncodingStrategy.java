package com.prgrms.shortener.domain;

public interface EncodingStrategy {

  String encode(Long id);
}
