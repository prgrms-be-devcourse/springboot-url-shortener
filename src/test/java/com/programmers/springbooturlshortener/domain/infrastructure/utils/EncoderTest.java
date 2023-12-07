package com.programmers.springbooturlshortener.domain.infrastructure.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Encoder {Base62, Base58}")
class EncoderTest {

    @DisplayName("Test Base62 encode")
    @Test
    void testBase62Encode() {
        // Arrange
        String url = "https://google.com";
        String expectedResult = "GEVv7DF";
        Encoder base62 = new Base62();
        // Act
        String actualResult = base62.encode(url);
        // Assert
        assertThat(actualResult).isEqualTo(expectedResult);
    }

}