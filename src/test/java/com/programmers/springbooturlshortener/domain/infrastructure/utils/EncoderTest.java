package com.programmers.springbooturlshortener.domain.infrastructure.utils;

import com.programmers.springbooturlshortener.domain.entity.EncodeType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Encoder {Base62, Base58}")
class EncoderTest {

    private static final String URL = "https://google.com";
    private static final int SHORT_URL_LENGTH = 7;

    @DisplayName("Test Base62 encode")
    @Test
    void testBase62Encode() {
        // Act
        String actualResult = EncodeType.BASE62.encode(URL);
        // Assert
        assertThat(actualResult.length()).isLessThanOrEqualTo(SHORT_URL_LENGTH);
    }

    @DisplayName("Test Base58 encode")
    @Test
    void testBase58Encode() {
        // Act
        String actualResult = EncodeType.BASE58.encode(URL);
        // Assert
        assertThat(actualResult.length()).isLessThanOrEqualTo(SHORT_URL_LENGTH);
        assertThat(actualResult).contains("-");
    }

}