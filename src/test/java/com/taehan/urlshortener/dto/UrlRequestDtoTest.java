package com.taehan.urlshortener.dto;

import com.taehan.urlshortener.model.AlgorithmType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class UrlRequestDtoTest {

    @CsvSource({"http://www.naver.com, www.naver.com",
            "https://naver.com, naver.com"
    })
    @DisplayName("url Scheme 제거 성공 테스트")
    @ParameterizedTest(name = "{index} {displayName}")
    void testRemoveHttpScheme(String urlWithScheme, String url) {
        UrlRequestDto requestDto = new UrlRequestDto(urlWithScheme, AlgorithmType.BASE62);

        assertThat(requestDto.getUrl()).isEqualTo(url);
    }

}