package com.prgrms.shortener.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class JpaShortenedUrlFactoryTest {

  @Autowired
  private JpaShortenedUrlFactory urlFactory;

  @Autowired
  private JpaShortenedUrlRepository urlRepository;

  @Test
  @DisplayName("JpaRepository에서 index로 사용할 entity id르 가져와야 한다.")
  @Transactional
  void createShortenedUrl() {
    // Given
    String originalUrl = "http://naver.com";

    // When
    ShortenedUrl createdUrl = urlFactory.createShortenedUrl(originalUrl);

    // Then
    assertThat(createdUrl.getOriginalUrl()).isEqualTo(originalUrl);
    assertThat(createdUrl.getId()).isNotNull();
    assertThat(createdUrl.getShortenedKey()).matches("[\\d\\w]{8}");

  }
}
