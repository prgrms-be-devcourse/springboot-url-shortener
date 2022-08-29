package com.programs.shorturl.util;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class Base58 {

  private static final char[] list = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();

  public String generateShortUrl() {

    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < 7; i++) {
      int index = random.nextInt(58);
      sb.append(list[index]);
    }
    return sb.toString();
  }

}
