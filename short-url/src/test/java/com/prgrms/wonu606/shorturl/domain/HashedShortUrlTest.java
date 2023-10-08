package com.prgrms.wonu606.shorturl.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class HashedShortUrlTest {

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
    void givenValidHashedUrl_CreatingHashedShortUrl(String validHash) {
        assertThatCode(() -> new HashedShortUrl(validHash))
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
    void givenInvalidHashedUrl_throwException(String invalidHash) {
        assertThatThrownBy(() -> new HashedShortUrl(invalidHash))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
