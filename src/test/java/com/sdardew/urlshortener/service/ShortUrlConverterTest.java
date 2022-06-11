package com.sdardew.urlshortener.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ShortUrlConverterTest {

  static ShortUrlConverter shortUrlGenerator = new ShortUrlConverter();

  @Test
  @DisplayName("length가 8 보다 큰 shortURL을 생성할 때 에러가 발생한다")
  public void testEncode() {
    assertThrows(IllegalArgumentException.class, () -> shortUrlGenerator.encode(Long.MAX_VALUE));
  }

  @Test
  @DisplayName("shortUrl을 decode하여 id를 찾을 수 있다")
  public void testDecode() {
    String shortUrl = shortUrlGenerator.encode(1L);
    assertThat(shortUrlGenerator.decode(shortUrl)).isEqualTo(1L);
  }
}