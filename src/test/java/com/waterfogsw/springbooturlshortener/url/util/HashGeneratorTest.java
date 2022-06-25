package com.waterfogsw.springbooturlshortener.url.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.waterfogsw.springbooturlshortener.url.entity.HashType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HashGeneratorTest {

  private static final Logger log = LoggerFactory.getLogger(HashGeneratorTest.class);
  private final HashGenerator hashGenerator = new HashGenerator();

  @Nested
  @DisplayName("generate 메서드는")
  class DescribeCreate {

    @Nested
    @DisplayName("str 값이 주어지지 않으면")
    class ContextWithNullOrEmptyStr {

      @ParameterizedTest
      @NullAndEmptySource
      @DisplayName("IllegalArgumentException 에러를 발생시킨다")
      void ItThrowsIllegalArgumentException(String src) {
        //when, then
        assertThrows(IllegalArgumentException.class,
            () -> hashGenerator.generate(src, HashType.SHA256, 1));
      }
    }

    @Nested
    @DisplayName("hashType 이 null 이면")
    class ContextWithHashTypeisNull {

      @Test
      @DisplayName("IllegalArgumentException 에러를 발생시킨다")
      void ItThrowsIllegalArgumentException() {
        //when, then
        assertThrows(IllegalArgumentException.class,
            () -> hashGenerator.generate("test", null, 1));
      }
    }

    @Nested
    @DisplayName("문자열과 hashType, 유효한 값이 전달되면")
    class ContextWithStringAndHashTypeAndPositive1 {

      @ParameterizedTest
      @EnumSource(HashType.class)
      @DisplayName("해시값을 반환한다")
      void ItReturnHashValue(HashType hashType) {
        //given
        final var hashLength = 10;

        //when
        final var hash = hashGenerator.generate("test", hashType, hashLength);

        //then
        assertEquals(hashLength, hash.length());
      }
    }

    @Nested
    @DisplayName("MD5 해시타입에 1부터 32사이의 값이 전달되면")
    class ContextWithStringAndHashTypeAndPositiveMD5 {

      @ParameterizedTest
      @ValueSource(ints = {1, 32})
      @DisplayName("해시값을 반환한다")
      void ItReturnHashValue(int src) {
        //when
        final var hash = hashGenerator.generate("test", HashType.MD5, src);

        //then
        assertEquals(src, hash.length());
      }
    }

    @Nested
    @DisplayName("MD5 해시타입에 1부터 32사이값이 아닌 값이 길이로 전달되면")
    class ContextWithStringAndHashTypeAndIllegalLengthMD5 {

      @ParameterizedTest
      @ValueSource(ints = {0, 33})
      @DisplayName("IllegalArgumentException 에러를 던진다")
      void ItThrowsIllegalArgumentException(int src) {
        //when, then
        assertThrows(IllegalArgumentException.class, () ->
            hashGenerator.generate("test", HashType.MD5, src));
      }
    }

    @Nested
    @DisplayName("SHA-256 해시타입에 1부터 64사이의 값이 전달되면")
    class ContextWithStringAndHashTypeAndPositiveSHA256 {

      @ParameterizedTest
      @ValueSource(ints = {1, 64})
      @DisplayName("해시값을 반환한다")
      void ItReturnHashValue(int src) {
        //when
        final var hash = hashGenerator.generate("test", HashType.SHA256, src);

        //then
        assertEquals(src, hash.length());
      }
    }

    @Nested
    @DisplayName("SHA256 해시타입에 1부터 32사이값이 아닌 값이 길이로 전달되면")
    class ContextWithStringAndHashTypeAndIllegalLengthSHA256 {

      @ParameterizedTest
      @ValueSource(ints = {0, 65})
      @DisplayName("IllegalArgumentException 에러를 던진다")
      void ItThrowsIllegalArgumentException(int src) {
        //when, then
        assertThrows(IllegalArgumentException.class, () ->
            hashGenerator.generate("test", HashType.SHA256, src));
      }
    }
  }
}
