package com.ryu.urlshortner.url.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlTest {

    @Nested
    class increaseHits_메소드는 {

        @Nested
        class 호출되면 {

            @Test
            void 단축된_URL에_대한_요청_수를_증가시킨다 () {
                //given
                final Url url = Url.builder()
                        .seq(10000000L)
                        .originUrl("https://www.test.com")
                        .shortUrl("http://localhost:8080/api/v1/urls/TEST123")
                        .expiredAt(LocalDateTime.of(
                                2022, 12, 25, 00, 00, 00
                        ))
                        .build();

                //when
                url.increaseHits();

                //then
                assertThat(url).extracting("hits").isEqualTo(1L);
            }
        }
    }
}
