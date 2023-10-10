package com.prgrms.shorturl.url.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlsTest {

    @Test
    @DisplayName("origin url이 null이거나 빈 값으로 Urls를 만들려고 시도하는 경우 예외를 던진다")
    void createUrlsWithOriginUrl_nullOrBlank_throwException() {
        //when_then
        assertThrows(IllegalArgumentException.class, () -> new Urls(null));
        assertThrows(IllegalArgumentException.class, () -> new Urls(""));
        assertThrows(IllegalArgumentException.class, () -> new Urls(" "));
    }

}
