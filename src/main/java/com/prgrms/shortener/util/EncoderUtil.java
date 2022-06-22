package com.prgrms.shortener.util;

import org.springframework.stereotype.Component;

@Component
public class EncoderUtil {

  private final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  public String encoding(Long id) {

    StringBuilder sb = new StringBuilder();
    final int division = BASE62.length();

    while (id > 0) {
      sb.insert(0, BASE62.charAt((int) (id % division)));
      id /= division;
    }

    return sb.toString();
  }
}
