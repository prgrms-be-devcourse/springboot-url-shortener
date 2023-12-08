package com.programmers.springbooturlshortener.domain.entity;

import com.programmers.springbooturlshortener.domain.exception.LongUrlFormatMismatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.programmers.springbooturlshortener.common.exception.ExceptionMessage.LONG_URL_FORMAT_MISMATCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test LongUrl Value")
class LongUrlTest {

    @Test
    void testCreateLongUrlSuccess() {
        // Arrange
        String expectedUrl = "https://google.com";
        // Act
        LongUrl actualResult = LongUrl.from(expectedUrl);
        // Assert
        assertThat(actualResult.getValue()).isEqualTo(expectedUrl);
    }

    @Test
    void testCreateLongUrlFail() {
        // Arrange
        String expectedUrl = "temp";
        // Act & Assert
        Throwable actualResult = assertThrows(LongUrlFormatMismatchException.class, () -> LongUrl.from(expectedUrl));
        assertThat(actualResult).hasMessage(LONG_URL_FORMAT_MISMATCH.getValue());
    }
}