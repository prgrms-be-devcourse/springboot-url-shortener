package com.example.urlmanagement.domain;

import com.example.urlmanagement.exception.UrlErrorCode;
import com.example.urlmanagement.exception.UrlException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class EncodeTypeTest {

    @Test
    @DisplayName("잘못된 EncodeType 입력은 예외를 발생시킨다.")
    void testInvalidUrlException() {
        // given
        String encodeType = "abcde";

        // when-then
        assertThatThrownBy(() -> EncodeType.getEncodeTypeByName(encodeType))
                .isInstanceOf(UrlException.class)
                .hasFieldOrPropertyWithValue("errorCode", UrlErrorCode.ENCODE_TYPE_NOT_FOUND);
    }
}
