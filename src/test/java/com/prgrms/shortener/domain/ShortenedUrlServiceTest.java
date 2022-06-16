package com.prgrms.shortener.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prgrms.shortener.domain.dto.UrlMetadata;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShortenedUrlServiceTest {

  private static final String ORIGINAL_URL = "http://naver.com";
  private static final String KEY = "succEss";
  @InjectMocks
  private ShortenedUrlService shortenedUrlService;
  @Mock
  private ShortenedUrlRepository repository;

  @Mock
  private ShortenedUrlFactory urlFactory;

  @Test
  @DisplayName("db에서 이미 url이 등록되어 있으면 해당 url의 key값을 반환한다.")
  void should_return_saved_key_when_url_is_already_stored() {

    // Given
    ShortenedUrl storedUrl = new ShortenedUrl();
    String storedKey = "yaHoo12";
    storedUrl.assignKey(storedKey);
    storedUrl.assignOriginalUrl(ORIGINAL_URL);

    // When
    when(repository.findByOriginalUrl(ORIGINAL_URL)).thenReturn(Optional.of(storedUrl));
    String key = shortenedUrlService.shorten(ORIGINAL_URL);

    // Then
    assertThat(key).isEqualTo(storedKey);

  }

  @Test
  @DisplayName("요청한 url이 저장소에 존재하지 않으면, 새로운 key 값을 생성해서 반환해야 한다.")
  void delegate_creation_to_factory() {

    // Given
    ShortenedUrl createdUrl = new ShortenedUrl(1);
    createdUrl.assignKey(KEY);
    createdUrl.assignOriginalUrl(ORIGINAL_URL);

    // When
    when(repository.findByOriginalUrl(ORIGINAL_URL)).thenReturn(Optional.empty());
    when(urlFactory.createShortenedUrl(ORIGINAL_URL)).thenReturn(createdUrl);
    String returnedKey = shortenedUrlService.shorten(ORIGINAL_URL);

    // Then
    verify(urlFactory, times(1)).createShortenedUrl(ORIGINAL_URL);
    assertThat(returnedKey).isEqualTo(KEY).matches("[\\w\\d]{7}");
  }

  @Test
  @DisplayName("key를 이용하여 repository에 저장된 OriginalUrl을 조회수를 갱신한 뒤 가져올 수 있어야 한다.")
  void delegates_search_by_key_to_repository() {

    // given
    ShortenedUrl savedUrl = new ShortenedUrl(1);
    savedUrl.assignKey(KEY);
    savedUrl.assignOriginalUrl(ORIGINAL_URL);

    // When
    when(repository.findByShortenedKey(KEY)).thenReturn(Optional.of(savedUrl));
    String originalUrl = shortenedUrlService.findOriginalUrlByKey(KEY);

    // Then
    verify(repository, times(1)).increaseCount(savedUrl.getId());
    assertThat(originalUrl).isEqualTo(ORIGINAL_URL);

  }

  @Test
  @DisplayName("매개변수로 주어진 key로 ShortenedUrl의 메타데이터를 반환해야 한다.")
  void get_metadata_using_key() {

    // when - 데이터가 저장되어 있을 경우에
    ShortenedUrl savedUrl = new ShortenedUrl(1);
    savedUrl.assignKey(KEY);
    savedUrl.assignOriginalUrl(ORIGINAL_URL);

    // when
    when(repository.findByShortenedKey(KEY)).thenReturn(Optional.of(savedUrl));
    UrlMetadata metadata = shortenedUrlService.getUrlMetadata(KEY);

    // Then
    assertThat(metadata.getKey()).isEqualTo(KEY);
    assertThat(metadata.getCount()).isZero();
    assertThat(metadata.getOriginalUrl()).isEqualTo(ORIGINAL_URL);

  }

}
