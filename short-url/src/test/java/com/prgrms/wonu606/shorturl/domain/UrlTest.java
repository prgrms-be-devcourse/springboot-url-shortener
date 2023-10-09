package com.prgrms.wonu606.shorturl.domain;


import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UrlTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "http://example.com",
            "https://example.com",
            "http://www.example.com",
            "https://www.example.com",
            "http://example.com/path",
            "https://example.com/path",
            "http://example.com/path#fragment",
            "ftp://example.com",
            "ftp://user:password@example.com",
            "ftp://user@example.com",
            "ftp://example.com"
    })
    void givenValidUrl_thenCreatingUrl(String validUrl) {
        // When & Then
        assertThatCode(() -> new Url(validUrl))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "invalidUrl",
            "http:/example.com",
            "https:/example.com",
            "http//example.com",
            "https//example.com",
            "httpx://www.naver.com",
    })
    void givenInvalidUrl_thenThrowException(String invalidUrl) {
        // When & Then
        assertThatThrownBy(() -> new Url(invalidUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 URL 주소입니다. Current URL: " + invalidUrl);
    }

    @Test
    void givenLongUrl_throwException() {
        // Given
        String longUrl = "https://example.com/%s".formatted("a".repeat(2_000));

        assertThatThrownBy(() -> new Url(longUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("URL의 길이는 %d를 넘어갈 수 없습니다 Current Length: %d", Url.URL_MAX_LENGTH, longUrl.length());
    }
}
