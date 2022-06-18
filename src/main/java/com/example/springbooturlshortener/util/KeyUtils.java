package com.example.springbooturlshortener.util;

public interface KeyUtils {

  String createKey(Long id);

  int decodeKey(String key);
}
