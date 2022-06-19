package com.example.springbooturlshortener.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import com.example.springbooturlshortener.domain.Url;
import com.example.springbooturlshortener.domain.UrlRepository;
import com.example.springbooturlshortener.exception.CustomException;
import com.example.springbooturlshortener.util.KeyUtils;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UrlServiceTest {

  @InjectMocks
  private UrlService urlService;

  @Mock
  private KeyUtils keyUtils;

  @Mock
  private UrlRepository urlRepository;

  private final String originalUrl = "https://programmers.co.kr/";
  private final String key = "akjsdfhk";

  @Nested
  class shortenUrl메서드는 {

    @DisplayName("긴 URL을 짧은 URL로 바꾼다")
    @Test
    void 긴_URL을_짧은_URL로_바꾼다() {
      //given
      Long id = 1000000L;
      String key = "aldjkfl123";
      given(urlRepository.save(any(Url.class)))
        .willReturn(id);
      given(keyUtils.createKey(anyLong()))
        .willReturn(key);

      //when
      String shortenUrl = urlService.shortenUrl(originalUrl);

      //then
      assertThat(shortenUrl).isNotBlank();
    }

    @Nested
    class URL이_null이거나_빈_값이거나_공백이라면 {

      @DisplayName("CustomException 예외를 던진다")
      @ParameterizedTest
      @NullAndEmptySource
      @ValueSource(strings = {" "})
      void CustomException_예외를_던진다(String url) {
        //given
        //when, then
        assertThatThrownBy(() ->
          urlService.shortenUrl(url))
          .isInstanceOf(CustomException.class)
          .hasMessage("잘못된 URL 정보입니다.");
      }
    }
  }

  @Nested
  class findOriginalUrl메서드는 {

    @DisplayName("짧은 URL에 해당하는 원래 URL을 반환한다")
    @Test
    void 짧은_URL에_해당하는_원래_URL을_반환한다() {
      //given
      Long id = 1L;
      Url url = new Url(originalUrl);
      given(keyUtils.decodeKey(anyString()))
        .willReturn(id);
      given(urlRepository.findById(anyLong()))
        .willReturn(Optional.of(url));

      //when
      String originalUrl = urlService.findOriginalUrl(key);

      //then
      verify(keyUtils).decodeKey(anyString());
      verify(urlRepository).findById(anyLong());
      assertThat(originalUrl).isNotBlank();
    }

    @Nested
    class Key가_null이거나_빈_값이거나_공백이라면 {

      @DisplayName("CustomException 예외를 던진다")
      @ParameterizedTest
      @NullAndEmptySource
      @ValueSource(strings = {" "})
      void CustomException_예외를_던진다(String key) {
        //given
        //when, then
        assertThatThrownBy(() ->
          urlService.findOriginalUrl(key))
          .isInstanceOf(CustomException.class)
          .hasMessage("잘못된 key 정보입니다.");
      }
    }

    @Nested
    class 존재하지_않는_Key값이라면 {

      @DisplayName("CustomException 예외를 던진다")
      @Test
      void CustomException_예외를_던진다() {
        //given
        given(urlRepository.findById(anyLong()))
          .willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() ->
          urlService.findOriginalUrl(key))
          .isInstanceOf(CustomException.class)
          .hasMessage("존재하지 않는 URL입니다.");
      }
    }
  }
}