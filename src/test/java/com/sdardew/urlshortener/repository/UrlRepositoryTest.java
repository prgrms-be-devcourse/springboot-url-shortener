package com.sdardew.urlshortener.repository;

import com.sdardew.urlshortener.model.Url;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@DataJpaTest
class UrlRepositoryTest {

  @Autowired
  private UrlRepository urlRepository;

  @PersistenceContext
  EntityManager entityManager;

  static Url testUrl;

  @BeforeAll
  static void beforeAll() {
    testUrl = new Url(1000L, "google.com", LocalDateTime.now(), 1L);
  }

  @Test
  @DisplayName("short url로 원래의 url을 알 수 있다")
  void testExistUrlByShortUrl() {
    Assertions.assertThat(urlRepository.existsUrlById(testUrl.getId())).isFalse();
    urlRepository.save(testUrl);
    entityManager.flush();
    Assertions.assertThat(urlRepository.existsUrlById(testUrl.getId())).isTrue();
  }
}