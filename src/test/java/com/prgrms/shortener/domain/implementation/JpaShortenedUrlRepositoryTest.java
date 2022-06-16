package com.prgrms.shortener.domain.implementation;

import static org.assertj.core.api.Assertions.assertThat;

import com.prgrms.shortener.domain.ShortenedUrl;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class JpaShortenedUrlRepositoryTest {

  @Autowired
  JpaShortenedUrlRepository jpaShortenedUrlRepository;

  @Test
  @DisplayName("DB에 저장된 ShortendUrl을 원본 url을 통해 찾을 수 있어야 한다.")
  void can_find_shortenedUrl_By_originalUrl() {

    // Given
    String url = "http://naver.com";
    String key = "aaaaaaa";
    ShortenedUrl shortenedUrl = new ShortenedUrl();
    shortenedUrl.assignKey(key);
    shortenedUrl.assignOriginalUrl(url);

    // When
    jpaShortenedUrlRepository.saveAndFlush(shortenedUrl);
    Optional<ShortenedUrl> savedUrl = jpaShortenedUrlRepository.findByOriginalUrl(url);

    // Then
    assertThat(savedUrl).isNotEmpty();
    assertThat(savedUrl.get().getOriginalUrl()).isEqualTo(url);
    assertThat(savedUrl.get().getShortenedKey()).isEqualTo(key);

  }

  @Test
  @DisplayName("db에 저장된 shortened url을 key 값으로 검색할 수 있어야 한다.")
  void can_find_shortenedUrl_By_Key() {

    // Given
    String url = "http://naver.com";
    String key = "aaaaaaa";
    ShortenedUrl shortenedUrl = new ShortenedUrl();
    shortenedUrl.assignKey(key);
    shortenedUrl.assignOriginalUrl(url);
    jpaShortenedUrlRepository.saveAndFlush(shortenedUrl);

    // When

    Optional<ShortenedUrl> savedUrl = jpaShortenedUrlRepository.findByShortenedKey(key);

    // Then
    assertThat(savedUrl).isNotEmpty();
    assertThat(savedUrl.get().getOriginalUrl()).isEqualTo(url);
    assertThat(savedUrl.get().getShortenedKey()).isEqualTo(key);

  }

  @Test
  @DisplayName("increaseCount 메서드를 호출하면 id와 일치하는 ShortenedUrl의 조회수를 1 증가시킨다.")
  void increaseCount_successful_case() {

    // Given
    ShortenedUrl savedUrl = new ShortenedUrl();
    savedUrl = jpaShortenedUrlRepository.save(savedUrl);

    // When
    jpaShortenedUrlRepository.increaseCount(savedUrl.getId());
    savedUrl = jpaShortenedUrlRepository.findById(1).get();
    // Then
    assertThat(savedUrl.getCount()).isEqualTo(1);

  }

}
