package com.example.springbooturlshortener.util;

import org.springframework.stereotype.Component;

@Component
public class Base62KeyUtils implements KeyUtils {

  private final char[] BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

  @Override
  public String createKey(Long id) {
    StringBuffer sb = new StringBuffer();
    while (id > 0) {
      sb.append(BASE62[(int) (id % 62)]);
      id /= 62;
    }
    return sb.toString();
  }

  @Override
  public int decodeKey(String key) {
    int result = 0;
    int power = 1;
    for (int i = 0; i < key.length(); i++) {
      int digit = new String(BASE62).indexOf(key.charAt(i));
      result += digit * power;
      power *= 62;
    }
    return result;
  }
}
