package com.prgrms.shortener.domain.implementation;

import com.prgrms.shortener.domain.encoding.EncodingStrategy;
import org.springframework.stereotype.Component;

@Component
public class Base62EncodingStrategy implements EncodingStrategy {

  private final String[] table = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("");

  @Override
  public String encode(Integer number) {

    StringBuilder buffer = new StringBuilder(7);

    for (int i = 6; i >= 0; i--) {
      int share = number / (int) Math.pow(62, i);
      buffer.append(table[share]);
      number -= share * (int) Math.pow(62, i);
    }

    return buffer.toString();
  }
}
