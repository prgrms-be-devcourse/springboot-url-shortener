package com.prgrms.urlshortener.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import com.prgrms.urlshortener.exception.InvalidShortedUrlException;

class ShortedUrlTest {

    @DisplayName("단축된 URL을 생성한다.")
    @Test
    void generate_shortedUrl() {
        // given
        String shortUrl = "1234567";

        // when
        ShortedUrl shortedUrl = new ShortedUrl(shortUrl);

        // then
        assertThat(shortedUrl).isNotNull();
    }

    @DisplayName("단축된 Url은 null일 수 없다.")
    @ParameterizedTest
    @NullSource
    void generate_shortedUrl_null(String shortedUrl) {
        assertThatThrownBy(() -> new ShortedUrl(shortedUrl))
            .isInstanceOf(InvalidShortedUrlException.class)
            .hasMessage("단축된 URL은 비어있을 수 없습니다.");
    }

    @DisplayName("단축된 Url은 8자를 초과할 수 없다.")
    @Test
    void generate_shortedUrl_length_over_max() {
        assertThatThrownBy(() -> new ShortedUrl("123456789"))
            .isInstanceOf(InvalidShortedUrlException.class)
            .hasMessage("단축된 URL은 8자를 넘을 수 없습니다.");
    }

}
