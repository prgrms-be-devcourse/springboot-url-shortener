package com.sdardew.urlshortener.service;

import org.springframework.stereotype.Component;

@Component
public class ShortUrlConverter {

  static final char[] BASE62 = {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u' ,'v', 'w', 'x', 'y', 'z',
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
  };
  private final int RADIX = 62;

  public String encode(long id) {
    if(218_340_105_584_896L < id) throw new IllegalArgumentException("The short url is longer than 8.");

    StringBuffer sb = new StringBuffer();
    while(0 < id) {
      sb.insert(0, BASE62[(int) (id % RADIX)]);
      id /= RADIX;
    }
    return sb.toString();
  }

  public long decode(String shortUrl) {
    long id = 0L;
    for(char c: shortUrl.toCharArray()) {
      if ('A' <= c && c <= 'Z') {
        id = id * 62 + c - 'A';
      } else if ('a' <= c && c <= 'z') {
        id = id * 62 + c - 'a' + 26;
      } else if ('0' <= c && c <= '9') {
        id = id * 62 + c - '0' + 52;
      }

    }
    return id;
  }
}
