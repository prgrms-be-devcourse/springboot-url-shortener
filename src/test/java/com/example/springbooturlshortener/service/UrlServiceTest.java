package com.example.springbooturlshortener.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import com.example.springbooturlshortener.domain.Url;
import com.example.springbooturlshortener.domain.UrlRepository;
import com.example.springbooturlshortener.exception.CustomException;
import com.example.springbooturlshortener.util.UniqueKeyUtils;
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
  private UniqueKeyUtils uniqueKeyUtils;

  @Mock
  private UrlRepository urlRepository;

  private final String originalUrl = "https://programmers.co.kr/";

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
      given(uniqueKeyUtils.createKey(anyLong()))
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
}