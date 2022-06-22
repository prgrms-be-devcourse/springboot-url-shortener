package com.prgrms.shortener.service;

import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.prgrms.shortener.entity.Url;
import com.prgrms.shortener.entity.UrlRepository;
import com.prgrms.shortener.exception.custom.NotFoundException;
import com.prgrms.shortener.util.EncoderUtil;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

  @Mock
  UrlRepository urlRepository;

  @Mock
  EncoderUtil encoderUtil;

  @InjectMocks
  UrlService urlService;

  @Nested
  @DisplayName("createShortUrl 메서드는 테스트 할 때")
  class DescribeCreateShortUrl {

    @Nested
    @DisplayName("인자로 null이 들어오면")
    class ContextNullURL {

      final String url = null;

      @Test
      @DisplayName("IllegalArgumentException을 던진다.")
      void itThrowIllegalArgumentException() {

        Assertions.assertThatThrownBy(() -> urlService.createShortUrl(url))
            .isInstanceOf(IllegalArgumentException.class);
      }
    }

    @Nested
    @DisplayName("이미 존재하는 url을 인자로 받으면")
    class ContextExistUrl {

      final String existOriginalUrl = "hello.com";
      final String uniqueKey = "AX3X";
      final String APP_URL = "localhost:8080/";

      @Test
      @DisplayName("해당 key의 url을 반환한다.")
      void itReturnExistUniqueKey() {

        Optional<Url> existUrl = Optional.of(Url.builder()
            .originalUrl(existOriginalUrl)
            .build());

        existUrl.get().saveUniqueKey(uniqueKey);
        when(urlRepository.findByOriginalUrl(existOriginalUrl)).thenReturn(existUrl);
        ReflectionTestUtils.setField(urlService, "APP_URL", "localhost:8080/");

        String expectedUniqueKey = urlService.createShortUrl(existOriginalUrl);

        Assertions.assertThat(expectedUniqueKey).isEqualTo(APP_URL + uniqueKey);
      }
    }

    @Nested
    @DisplayName("존재하지 않는 url을 인자로 받으면")
    class ContextNewUrl {

      final String url = "hello.com";
      final String uniqueKey = "AX3X";
      final String APP_URL = "localhost:8080/";

      @Test
      @DisplayName("새로운 key의 url을 반환한다.")
      void itReturnNewUniqueKey() {

        ReflectionTestUtils.setField(urlService, "APP_URL", "localhost:8080/");
        when(encoderUtil.encoding(any())).thenReturn(uniqueKey);
        String expectedUniqueKey = urlService.createShortUrl(url);

        Assertions.assertThat(expectedUniqueKey).isEqualTo(APP_URL + uniqueKey);
      }
    }
  }

  @Nested
  @DisplayName("findByUniqueKey 메서든는 테스트 할 때")
  class DescribeFindByUniqueKey {

    @Nested
    @DisplayName("인자로 null을 받으면")
    class ContextReceiveNull {

      final String uniqueKey = null;

      @Test
      @DisplayName("IllegalArgumentException 예외를 던진다.")
      void itReturnIllegalArgumentException() {

        Assertions.assertThatThrownBy(() -> urlService.findByUniqueKey(uniqueKey))
            .isInstanceOf(IllegalArgumentException.class);
      }
    }

    @Nested
    @DisplayName("인자로 존재하지 않는 key를 받으면")
    class ContextReceiveNotExistKey {

      final String notExistUniqueKey = "AAA";

      @Test
      @DisplayName("NotFoundException 예외를 던진다.")
      void itReturnNotFoundException() {

        when(urlRepository.findByUniqueKey(notExistUniqueKey)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> urlService.findByUniqueKey(notExistUniqueKey))
            .isInstanceOf(NotFoundException.class);
      }
    }

    @Nested
    @DisplayName("존재하는 key를 받으면")
    class ContextReceiveExistKey {

      final String existUniqueKey = "AAA";
      final String originalUrl = "hello.com";

      @Test
      @DisplayName("해당 key를 가진 원본 url을 반환한다.")
      void itReturnOriginalUrl() {

        Url url = Url.builder()
            .originalUrl(originalUrl)
            .build();

        url.saveUniqueKey(existUniqueKey);
        when(urlRepository.findByUniqueKey(existUniqueKey)).thenReturn(Optional.of(url));

        String expectedOriginalUrl = urlService.findByUniqueKey(existUniqueKey);

        Assertions.assertThat(originalUrl).isEqualTo(expectedOriginalUrl);
      }
    }
  }
}