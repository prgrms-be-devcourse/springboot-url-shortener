package com.example.springbooturlshortener.util;

public interface KeyUtils {

  String createKey(Long id);

  Long decodeKey(String key);
}
