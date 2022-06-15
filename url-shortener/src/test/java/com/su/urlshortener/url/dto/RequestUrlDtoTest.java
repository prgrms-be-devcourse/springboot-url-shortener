package com.su.urlshortener.url.dto;


import com.su.urlshortener.url.service.shortener.ShorteningAlgorithm;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RequestUrlDtoTest {

    private String testUrl = "http://ndcreplay.nexon.com";

    @Nested
    class UrlRequestDto클래스를 {
        @ParameterizedTest
        @EnumSource(value = ShorteningAlgorithm.class, names = {"SEQUENCE", "RANDOM"})
        void 생성할_수_있다(ShorteningAlgorithm algorithm) {
            var dto = RequestUrlDto.of(algorithm, testUrl);
            assertThat(dto).isInstanceOf(RequestUrlDto.class);

            assertAll(
                    () -> assertThat(dto).hasFieldOrProperty("algorithm"),
                    () -> assertThat(dto).hasFieldOrProperty("originUrl")
            );
        }

    }
}