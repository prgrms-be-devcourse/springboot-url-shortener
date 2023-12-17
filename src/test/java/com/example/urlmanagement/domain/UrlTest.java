package com.example.urlmanagement.domain;

import com.example.urlmanagement.exception.UrlErrorCode;
import com.example.urlmanagement.exception.UrlException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UrlTest {

    @Test
    @DisplayName("Url 생성 시 잘못된 URL 형식은 예외를 발생시킨다.")
    void testInvalidUrlException() {
        // given
        String originalUrl = "abcde";

        // when-then
        assertThatThrownBy(() -> new Url(originalUrl))
                .isInstanceOf(UrlException.class)
                .hasFieldOrPropertyWithValue("errorCode", UrlErrorCode.INVALID_URL);
    }
}
