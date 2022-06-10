package org.prgrms.kdt.url;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgrms.kdt.url.generator.IdGenerator;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceTest {

  @InjectMocks
  ShortUrlService service;

  @Mock
  ShortUrlRepository repository;

  @Mock
  IdGenerator generator;

  @Mock
  Map<String, IdGenerator> generators;

  ShortUrl shortUrl = new ShortUrl("abc", "https://www.exmaple.com", 0);

  @Test
  @DisplayName("단축 URL ID로 단축 URL 정보를 불러온다.")
  void testGetShortUrlInfo() {
    given(repository.findById("abc")).willReturn(Optional.ofNullable(shortUrl));

    ShortUrl retrievedUrl = service.getShortUrl("abc");

    then(repository).should().findById("abc");
    assertThat(retrievedUrl).usingRecursiveComparison().isEqualTo(shortUrl);
  }

  @Test
  @DisplayName("단축 URL ID로 원본 URL을 불러온다.")
  void testGetOriginalUrl() {
    given(repository.findById("abc")).willReturn(Optional.ofNullable(shortUrl));

    String originalUrl = service.getOriginalUrl("abc");

    then(repository).should().findById("abc");
    then(repository).should().save(shortUrl.increasedCountUrl());
    assertThat(originalUrl).isEqualTo("https://www.exmaple.com");
  }

  @Test
  @DisplayName("원본 URL을 단축 URL로 반환한다.")
  void testShortenUrl() {
    given(generators.get(anyString())).willReturn(generator);
    given(generator.generate()).willReturn("abc");
    given(repository.save(shortUrl)).willReturn(shortUrl);

    ShortUrl url = service.shortenUrl("https://www.exmaple.com", "base62");

    then(generators).should().get("base62");
    then(generator).should().generate();
    then(repository).should().save(shortUrl);
    assertThat(url).usingRecursiveComparison().isEqualTo(shortUrl);
  }
}