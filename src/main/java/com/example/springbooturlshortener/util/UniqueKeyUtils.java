package com.example.springbooturlshortener.util;

public interface UniqueKeyUtils {

  String createKey(Long id);

  int decodeKey(String key);
}
