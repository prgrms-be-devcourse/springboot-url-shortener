package com.waterfogsw.springbooturlshortener.url.serivce;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.waterfogsw.springbooturlshortener.common.properties.AppProperties;
import com.waterfogsw.springbooturlshortener.url.entity.HashType;
import com.waterfogsw.springbooturlshortener.url.entity.Url;
import com.waterfogsw.springbooturlshortener.url.repository.UrlRepository;
import com.waterfogsw.springbooturlshortener.url.util.HashGenerator;
import com.waterfogsw.springbooturlshortener.url.util.UrlBase64Encoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class DefaultUrlServiceTest {

  private final Logger log = LoggerFactory.getLogger(DefaultUrlService.class);

  @Mock
  private AppProperties appProperties;

  @Mock
  private UrlBase64Encoder urlBase64Encoder;

  @Mock
  private UrlRepository urlRepository;

  @Mock
  private HashGenerator hashGenerator;

  @InjectMocks
  private DefaultUrlService defaultUrlService;

  @Nested
  @DisplayName("shorten 메서드는")
  class DescribeShorten {

    @Nested
    @DisplayName("유효한 url이 전달되면")
    class ContextWithValidUrl {

      @ParameterizedTest
      @EnumSource(HashType.class)
      @DisplayName("Short url 을 반환한다")
      void ItCreateEntity(HashType hashType) {
        //given
        final var testUrl = "https://www.naver.com";
        final var hashValue = "hashValue";
        final var encodedValue = "encodedValue";
        final var url = Url.builder()
            .orgUrl(testUrl)
            .hash(hashValue)
            .hashType(hashType)
            .build();

        given(hashGenerator.generate(anyString(), any(HashType.class), anyInt()))
            .willReturn(hashValue);
        given(urlRepository.save(any(Url.class)))
            .willReturn(url);
        given(urlBase64Encoder.encode(anyString()))
            .willReturn(encodedValue);
        given(appProperties.getUrl())
            .willReturn("http://localhost:8080");

        //when
        final var result = defaultUrlService.shorten(testUrl, hashType);

        //then
        verify(urlRepository).save(any(Url.class));
        log.info(result);
      }
    }

    @Nested
    @DisplayName("유효하지 않은 url 이 전달되면")
    class ContextWithInvalidUrl {

      @ParameterizedTest
      @NullAndEmptySource
      @ValueSource(strings = {"test.com.com", "https://wwww.aaaa.cccc"})
      @DisplayName("IllegalArgumentException 에러를 발생시킨다")
      void ItThrowsIllegalArgumentException(String srcUrl) {
        //when, then
        assertThrows(IllegalArgumentException.class,
            () -> defaultUrlService.shorten(srcUrl, HashType.SHA256));

      }
    }

    @Nested
    @DisplayName("HashType 에 Null 값이 전달되면")
    class ContextWithNullHashType {

      @Test
      @DisplayName("IllegalArgumentException 에러를 발생시킨다")
      void ItThrowsIllegalArgumentException() {
        //given
        final var srcUrl = "https://www.naver.com";
        //when, then
        assertThrows(IllegalArgumentException.class,
            () -> defaultUrlService.shorten(srcUrl, null));

      }
    }


  }
}