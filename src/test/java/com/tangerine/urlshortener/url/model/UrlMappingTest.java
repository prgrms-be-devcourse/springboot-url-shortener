package com.tangerine.urlshortener.url.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.tangerine.urlshortener.url.model.vo.Algorithm;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.model.vo.ShortUrl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlMappingTest {

    @Test
    @DisplayName("유효한 값 객체 정보로 엔티티 생성한다.")
    void instance_Success() {
        // Given
        String originUrlText = "origin";
        String shortUrlText = "short";

        // When
        UrlMapping urlMapping = new UrlMapping(
                new OriginUrl(originUrlText),
                new ShortUrl(shortUrlText),
                Algorithm.BASE62);

        // Then
        assertThat(urlMapping.getOriginUrl()).isEqualTo(new OriginUrl(originUrlText));
        assertThat(urlMapping.getShortUrl()).isEqualTo(new ShortUrl(shortUrlText));
    }

    @Test
    @DisplayName("유효하지 않은 값 객체 정보로 엔티티 생성 실패한다.")
    void instance_fail() {
        // Given
        String originUrlText = "";

        // When
        Exception exception = catchException(() ->
                new UrlMapping(
                        new OriginUrl(originUrlText),
                        Algorithm.BASE62));

        // Then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

}