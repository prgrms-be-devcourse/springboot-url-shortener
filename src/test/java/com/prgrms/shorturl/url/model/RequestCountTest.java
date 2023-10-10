package com.prgrms.shorturl.url.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestCountTest {

    @Test
    @DisplayName("요청 횟수가 음수인 경우 예외를 던진다.")
    void isisValidated_negativeRequest_throwException() {
        //when_then
        assertThrows(IllegalArgumentException.class,()-> new RequestCount(-1));
    }

}
