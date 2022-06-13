package com.prgrms.urlshortener.domain.url;

import com.prgrms.urlshortener.domain.url.Url;
import com.prgrms.urlshortener.common.error.exception.WrongFieldException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlTest {
    @Test
    @DisplayName("Url 엔티티 생성시 url이 옳바르지 못한 경우 테스트")
    void testWrongFieldExceptionCauseByUrl() {
        assertThrows(WrongFieldException.class, () -> new Url(null, 0));
    }

    @Test
    @DisplayName("Url 엔티티 생성시 requestCount가 옳바르지 못한 경우 테스트")
    void testWrongFieldExceptionCauseByRequestCount() {
        assertThrows(WrongFieldException.class, () -> new Url("abc.com", -1));
    }
}