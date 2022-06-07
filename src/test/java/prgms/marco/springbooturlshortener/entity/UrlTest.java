package prgms.marco.springbooturlshortener.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlTest {

    @Test
    @DisplayName("생성 테스트")
    void testCreateSuccess() {
        //given when
        Url url = Url.createUrl("www.originUrl.com");

        //then
        assertAll(
            () -> assertThat(url).isNotNull(),
            () -> assertThat(url.getOriginUrl()).isEqualTo("www.originUrl.com"),
            () -> assertThat(url.getShortUrl()).isNull(),
            () -> assertThat(url.getReqCount()).isZero()
        );
    }
}