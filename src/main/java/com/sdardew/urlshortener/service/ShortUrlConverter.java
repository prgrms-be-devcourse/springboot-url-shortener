package com.sdardew.urlshortener.service;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component
public class ShortUrlConverter {

  static final char[] BASE62 = {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u' ,'v', 'w', 'x', 'y', 'z',
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
  };
  private final int RADIX = 62;

  public String encode(long id) {
    if(218_340_105_584_896L < id) throw new IllegalArgumentException("너무 큰 수입니다.");

    StringBuffer sb = new StringBuffer();
    while(id > 0) {
      sb.append(BASE62[(int) (id % RADIX)]);
      id /= RADIX;
    }
    return sb.toString();
  }

  // https://www.geeksforgeeks.org/how-to-design-a-tiny-url-or-url-shortener/
  // 조곰 생각해보깅
//  public long decode(String shortUrl) {
//    long ret = 0L;
//
//
//  }

/*  public long decode(String param) {
    long sum = 0;
    long power = 1;
    for (int i = 0; i < param.length(); i++) {
      sum += BASE62[i] * power;
      power *= RADIX;
    }
    return sum;
  }*/
}
