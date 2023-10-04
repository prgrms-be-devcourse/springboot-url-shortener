package com.tangerine.urlshortener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlTest {

    @Test
    @DisplayName("올바른 정보로 생성한다.")
    void instance_Success() {
        // Given
        String originUrlText = "origin";
        String shortUrlText = "short";

        // When
        Url url = new Url(
                new OriginUrl(originUrlText),
                new ShortUrl(shortUrlText),
                new Algorithm("알고리즘"),
                new RequestCount(0)
        );

        // Then
        assertThat(url.getOriginUrlText()).isEqualTo(originUrlText);
        assertThat(url.getShortUrlText()).isEqualTo(shortUrlText);
    }

    @Test
    @DisplayName("올바르지 않은 정보로 생성 실패한다.")
    void instance_fail() {
        // Given
        String originUrlText = "";
        String shortUrlText = "short";

        // When
        Exception exception = catchException(() ->
                new Url(
                        new OriginUrl(originUrlText),
                        new ShortUrl(shortUrlText),
                        new Algorithm(""),
                        new RequestCount(-1)
                ));

        // Then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

}