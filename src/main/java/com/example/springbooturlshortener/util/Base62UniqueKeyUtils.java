package com.example.springbooturlshortener.util;

import org.springframework.stereotype.Component;

@Component
public class Base62UniqueKeyUtils implements UniqueKeyUtils {

  @Override
  public String createKey(Long id) {
    return null;
  }

  @Override
  public String decodeKey(String key) {
    return null;
  }
}
