package com.programmers.urlshortener.url.domain;

import static com.programmers.urlshortener.common.exception.ExceptionRule.*;
import static com.programmers.urlshortener.url.domain.Algorithm.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.programmers.urlshortener.common.exception.UrlException;

class UrlTest {

    @ParameterizedTest
    @ValueSource(strings = {"https://", "www.naver.com", "changhyeon", "ftp://www.ftp.com"})
    @DisplayName("Url을 생성할 때 OriginalUrl이 URL형식이 아닌 경우 예외가 발생한다.")
    void originalUrl_isNotURLForm_ExceptionThrown(String originalURL) {
        // given & when & then
        assertThatThrownBy(() -> Url.builder()
            .originalUrl(originalURL)
            .algorithm(BASE62)
            .ip("0.0.0.0")
            .build())
            .isInstanceOf(UrlException.class)
            .hasMessage(BAD_REQUEST.getMessage());
    }

    @Test
    @DisplayName("데이터베이스에 저장되지 않은 URL은 ShortUrl로 변환 시킬 수 없다.")
    void cant_Convert_ShortUrl_WhenUrlNotSaved() {
        // given
        Url url = Url.builder()
            .originalUrl("https://www.naver.com")
            .algorithm(BASE62)
            .ip("0.0.0.0")
            .build();

        // when & then
        assertThatThrownBy(url::convertToShortUrl)
            .isInstanceOf(UrlException.class)
            .hasMessage("해당 URL은 데이터베이스에 저장된 URL이 아닙니다.");
    }

    @Test
    @DisplayName("URL은 직접 생성하면 조회 수를 증가 시킬 수 없다.")
    void cant_Increase_UrlCount_WhenUrlCreated() {
        // given
        Url url = Url.builder()
            .originalUrl("https://www.naver.com")
            .algorithm(BASE62)
            .ip("0.0.0.0")
            .build();

        // when & then
        assertThatThrownBy(url::increaseCount)
            .isInstanceOf(NullPointerException.class);
    }
}
