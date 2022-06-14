package com.prgrms.shortener.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShortenedUrlTest {

  @Test
  @DisplayName("ShortenedUrl에는 id, originalUrl, key, count가 있어야 한다")
  void field_test() {

    // Given
    ShortenedUrl shortenedUrl = new ShortenedUrl();
    String url = "http://kms.com";

    // When
    shortenedUrl.assignOriginalUrl(url);
    shortenedUrl.assignKey("abcdefg");

    // Then
    assertThat(shortenedUrl.getId()).isNull();
    assertThat(shortenedUrl.getOriginalUrl()).isEqualTo(url);
    assertThat(shortenedUrl.getShortenedKey()).isEqualTo("abcdefg");
    assertThat(shortenedUrl.getCount()).isZero();
  }

  @Test
  @DisplayName("key를 할당할 때 key의 길이가 7인지 확인해야 한다.")
  void assignKeyValidationTest() {

    // Given
    String wrongKey = "abcdefgh";
    ShortenedUrl shortenedUrl = new ShortenedUrl();

    assertThatThrownBy(() ->
        shortenedUrl.assignKey(wrongKey)
    ).hasMessage("key는 7개의 문자로 구성되어야 합니다.");
  }

  @Test
  @DisplayName("원본 url을 할당할 때 문자열 길이가 1000이 넘어가면 IllegalArgumentException을 발생시킨다.")
  void throws_illegal_argument_exception() {

    // Given
    StringBuilder chunk = new StringBuilder();
    for (int i = 0; i < 1000; i++) {
      chunk.append("a");
    }
    String longUrl = "http://" + chunk.toString();

    // When
    ShortenedUrl shortenedUrl = new ShortenedUrl();

    // Then
    assertThatThrownBy(() -> shortenedUrl.assignOriginalUrl(longUrl)).isInstanceOf(IllegalArgumentException.class);

  }

}
