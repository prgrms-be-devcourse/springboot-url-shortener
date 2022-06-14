package com.example.demo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlTest {

    @Test
    @DisplayName("유효하지않은 url을 입력하는 경우 에러가 발생한다")
    public void invalidShortUrlTest() {
        String invalidUrl = "asdkfljasdlfk";

        assertThrows(IllegalArgumentException.class, () -> new Url(invalidUrl, 1));
    }
}