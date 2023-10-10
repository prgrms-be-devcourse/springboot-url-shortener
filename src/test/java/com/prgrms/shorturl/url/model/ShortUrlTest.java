package com.prgrms.shorturl.url.model;

import com.prgrms.shorturl.Fixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ShortUrlTest {

    @Test
    @DisplayName("short url이 null이거나 빈 값인 경우 예외를 던진다.")
    void createShortUrl_nullOrBlank_throwException() {
        //when_then
        assertThrows(IllegalArgumentException.class, () -> new ShortUrl(null));
        assertThrows(IllegalArgumentException.class, () -> new ShortUrl(""));
        assertThrows(IllegalArgumentException.class, () -> new ShortUrl(" "));
    }

    @Test
    @DisplayName("short url의 길이가 최대 길이를 넘어가면 예외를 던진다.")
    void createShortUrl_overMaxLength_throwException() {
        //given
        ShortUrl url = Fixtures.oneLengthShortUrl();
        int maxLength = url.SHORT_URL_MAX_LENGTH;
        StringBuilder sb = new StringBuilder(url.getShortUrl());

        while (sb.length() <= maxLength) {
            sb.append("A");
        }

        //when_then
        assertThrows(IllegalArgumentException.class, () -> new ShortUrl(sb.toString()));
    }

}
