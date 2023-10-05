package com.tangerine.urlshortener.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.tangerine.urlshortener.model.vo.Algorithm;
import com.tangerine.urlshortener.model.vo.OriginUrl;
import com.tangerine.urlshortener.model.vo.RequestCount;
import com.tangerine.urlshortener.model.vo.ShortUrl;
import com.tangerine.urlshortener.model.UrlMapping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UrlMappingTest {

    @Test
    @DisplayName("올바른 정보로 생성한다.")
    void instance_Success() {
        // Given
        String originUrlText = "origin";
        String shortUrlText = "short";

        // When
        UrlMapping urlMapping = new UrlMapping(
                new OriginUrl(originUrlText),
                new ShortUrl(shortUrlText),
                new Algorithm("알고리즘"),
                new RequestCount(0)
        );

        // Then
        assertThat(urlMapping.getOriginUrlText()).isEqualTo(originUrlText);
        assertThat(urlMapping.getShortUrlText()).isEqualTo(shortUrlText);
    }

    @Test
    @DisplayName("올바르지 않은 정보로 생성 실패한다.")
    void instance_fail() {
        // Given
        String originUrlText = "";
        String shortUrlText = "short";

        // When
        Exception exception = catchException(() ->
                new UrlMapping(
                        new OriginUrl(originUrlText),
                        new ShortUrl(shortUrlText),
                        new Algorithm(""),
                        new RequestCount(-1)
                ));

        // Then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

}