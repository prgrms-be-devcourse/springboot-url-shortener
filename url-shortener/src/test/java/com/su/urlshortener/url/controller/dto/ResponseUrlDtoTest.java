package com.su.urlshortener.controller.dto;

import com.su.urlshortener.url.entity.Url;
import com.su.urlshortener.url.service.shortener.ShorteningAlgorithm;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.su.urlshortener.url.dto.ResponseUrlDto.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ResponseUrlDtoTest {

    @Nested
    class ResponseDtoFromUrl {
        @ParameterizedTest
        @EnumSource(value = ShorteningAlgorithm.class, names = {"SEQUENCE", "RANDOM"})
        void test(ShorteningAlgorithm algorithm) {
            var url = Url.of("origin", "12345678", algorithm);
            var dto = from(url);
            assertThat(dto).isInstanceOf(ResponseUrlDto.class);

            assertAll(
                    () -> assertThat(dto).hasFieldOrProperty("id"),
                    () -> assertThat(dto).hasFieldOrProperty("algorithm"),
                    () -> assertThat(dto).hasFieldOrProperty("originUrl"),
                    () -> assertThat(dto).hasFieldOrProperty("shotToken"),
                    () -> assertThat(dto).hasFieldOrProperty("visitNum"),
                    () -> assertThat(dto).hasFieldOrProperty("createdAt"),
                    () -> assertThat(dto).hasFieldOrProperty("updatedAt")
            );
        }
    }

}