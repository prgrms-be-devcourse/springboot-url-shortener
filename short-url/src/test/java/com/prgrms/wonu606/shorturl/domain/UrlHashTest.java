package com.prgrms.wonu606.shorturl.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UrlHashTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "12345678",
            "abcd1234",
            "a1b2c3d4",
            "1a2b3c4d",
            "abcd",
            "A",
            "A123"
    }
    )
    void givenValidHashedUrl_thenCreatingHashedShortUrl(String validHash) {
        assertThatCode(() -> new UrlHash(validHash))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "123456789",
            "abcdefghi",
            "a1b2c3d4e",
            "1a2b3c4d5"
    })
    void givenInvalidHashedUrl_thenThrowException(String invalidHash) {
        assertThatThrownBy(() -> new UrlHash(invalidHash))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
