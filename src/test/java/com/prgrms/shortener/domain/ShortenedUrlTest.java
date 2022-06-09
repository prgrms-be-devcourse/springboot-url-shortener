package com.prgrms.shortener.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShortenedUrlTest {

  @Test
  @DisplayName("ShortenedUrl에는 id, originalUrl, key가 있어야 한다")
  void field_test() {

    // Given
    ShortenedUrl shortenedUrl = new ShortenedUrl();
    String url = "http://kms.com";

    // When
    shortenedUrl.assignOriginalUrl(url);
    shortenedUrl.assignKey("abcdefgh");

    // Then
    assertThat(shortenedUrl.getId()).isNull();
    assertThat(shortenedUrl.getOriginalUrl()).isEqualTo(url);
    assertThat(shortenedUrl.getKey()).isEqualTo("abcdefgh");

  }

  @Test
  @DisplayName("key를 할당할 때 key의 길이가 8인지 확인해야 한다.")
  void assignKeyValidationTest() {

    // Given
    String wrongKey = "abcdefg";
    ShortenedUrl shortenedUrl = new ShortenedUrl();

    assertThatThrownBy(() -> {
      shortenedUrl.assignKey(wrongKey);
    }).hasMessage("key는 8개의 문자로 구성되어야 합니다.");
  }

}
