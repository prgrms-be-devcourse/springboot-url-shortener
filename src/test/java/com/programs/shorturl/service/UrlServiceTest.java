package com.programs.shorturl.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.programs.shorturl.dto.UrlRequestDto;
import com.programs.shorturl.dto.UrlResponseDto;
import com.programs.shorturl.exception.NotExistException;
import com.programs.shorturl.repository.UrlRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("Url 서비스 테스트")
class UrlServiceTest {

  @Autowired
  private UrlService urlService;

  @Autowired
  private UrlRepository urlRepository;

  private UrlRequestDto urlRequestDto;

  @BeforeEach
  void setup() {
    urlRequestDto = new UrlRequestDto("www.naver.com");
  }

  @AfterEach
  void clear() {
    urlRepository.deleteAll();
  }

  @Nested
  @DisplayName("short url 생성 테스트")
  class saveTest{

    @Test
    @DisplayName("스키마 붙이지 않은 url 변환 성공 테스트")
    void successNotUsingSchema() {
      UrlResponseDto url = urlService.createShortUrl(urlRequestDto);

      assertThat(url.getOriginalUrl()).isEqualTo("https://www.naver.com");
      assertThat(url.getShortUrl()).isNotEmpty();
    }

    @Test
    @DisplayName("스키마 붙인 url 변환 성공 테스트")
    void successUsingSchema() {
      urlRequestDto = new UrlRequestDto("https://www.naver.com");
      UrlResponseDto url = urlService.createShortUrl(urlRequestDto);

      assertThat(url.getOriginalUrl()).isEqualTo("https://www.naver.com");
      assertThat(url.getShortUrl()).isNotEmpty();
    }

    @Test
    @DisplayName("이미 존재하는 url인 경우 같은 shorturl 변환 ")
    void existShortUrl() {

      UrlResponseDto url = urlService.createShortUrl(urlRequestDto);
      urlRequestDto = new UrlRequestDto("https://www.naver.com");
      UrlResponseDto UsingSchema = urlService.createShortUrl(urlRequestDto);
      UrlResponseDto equalUrl = urlService.createShortUrl(urlRequestDto);

      assertThat(url.getOriginalUrl()).isEqualTo(UsingSchema.getOriginalUrl());
      assertThat(url.getOriginalUrl()).isEqualTo(equalUrl.getOriginalUrl());
      assertThat(url.getShortUrl()).isEqualTo(UsingSchema.getShortUrl());
      assertThat(url.getShortUrl()).isEqualTo(equalUrl.getShortUrl());
    }
  }

  @Nested
  @DisplayName("OriginalUrl 가져오기")
  class getOriginalUrlTest{

    @Test
    @DisplayName("OriginalUrl 가져오기 성공")
    void success() {
      UrlResponseDto url = urlService.createShortUrl(urlRequestDto);

      String originalUrl = urlService.getOriginalUrl(url.getShortUrl());

      assertThat(url.getOriginalUrl()).isEqualTo(originalUrl);
    }

    @Test
    @DisplayName("존재하지 않는 shortUrl 입니다.")
    void failNotExistShortUrl() {

      assertThatThrownBy(() -> urlService.getOriginalUrl("abcd123"))
      .isInstanceOf(NotExistException.class);

    }
  }
}