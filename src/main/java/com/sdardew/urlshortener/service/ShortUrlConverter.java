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

  public String encode(long param) {
    StringBuffer sb = new StringBuffer();
    while(param > 0) {
      sb.append(BASE62[(int) (param % RADIX)]);
      param /= RADIX;
    }
    return sb.toString();
  }

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
