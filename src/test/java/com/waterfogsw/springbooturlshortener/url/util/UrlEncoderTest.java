package com.waterfogsw.springbooturlshortener.url.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class UrlEncoderTest {

  private final UrlBase64Encoder urlEncoder = new UrlBase64Encoder();

  @Nested
  @DisplayName("encode 메서드는")
  class DescribeEncode {

    @Nested
    @DisplayName("문자열이 전달되면")
    class ContextWithString {

      @Test
      @DisplayName("인코딩된 값을 전달한다")
      void ItReturnEncodedValue() {
        //given
        final var testString = "testTest";

        //when
        final var encodedVal = urlEncoder.encode(testString);

        //then
        final var decodedVal = urlEncoder.decode(encodedVal);
        assertEquals(testString, decodedVal);
      }
    }

    @Nested
    @DisplayName("빈문자열이나 Null 값이 전달되면")
    class ContextWithNullOrEmptySource {

      @ParameterizedTest
      @NullAndEmptySource
      @DisplayName("IllegalArgumentException 에러를 발생시킨다")
      void ItThrowsIllegalArgumentException(String src) {
        //when, then
        assertThrows(IllegalArgumentException.class, () -> urlEncoder.encode(src));
      }
    }

  }
}
