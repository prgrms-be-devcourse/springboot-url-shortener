package com.prgrms.shortener.domain;

import org.springframework.stereotype.Component;

@Component
public class Base62EncodingStrategy implements EncodingStrategy {

  private final String[] table = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("");

  @Override
  public String encode(Integer number) {

    StringBuilder buffer = new StringBuilder(7);

    // 62^7 .... 62^0까지 나눈다.

    for (int i = 6; i >= 0; i--) {
      int share = number / (int) Math.pow(62, i);
      buffer.append(table[share]);
      number -= share * (int) Math.pow(62, i);
    }

    return buffer.toString();
  }
}
