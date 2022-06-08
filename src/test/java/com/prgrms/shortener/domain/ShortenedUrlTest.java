package com.prgrms.shortener.domain;

import static org.assertj.core.api.Assertions.assertThat;

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

}
