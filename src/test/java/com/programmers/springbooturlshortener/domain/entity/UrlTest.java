package com.programmers.springbooturlshortener.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Test Url Entity")
class UrlTest {

    private final String expectedLongUrl = "https://google.com";
    private final EncodeType expectedEncodeType = EncodeType.BASE62;
    private final String expectedShortUrl = expectedEncodeType.encode(expectedLongUrl);

    @DisplayName("Test Create Url")
    @Test
    void testCreateUrl() {
        // Act
        Url actualResult = Url.builder()
                .longUrl(expectedLongUrl)
                .shortUrl(expectedShortUrl)
                .encodeType(expectedEncodeType)
                .build();
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getLongUrl()).isEqualTo(expectedLongUrl);
        assertThat(actualResult.getShortUrl()).isEqualTo(expectedShortUrl);
        assertThat(actualResult.getEncodeType()).isEqualTo(expectedEncodeType);
        assertThat(actualResult.getHit()).isZero();
    }

    @DisplayName("Test Update Hit")
    @Test
    void testUpdateHitSuccess() {
        // Arrange
        Url actualResult = Url.builder()
                .longUrl(expectedLongUrl)
                .shortUrl(expectedShortUrl)
                .encodeType(expectedEncodeType)
                .build();
        // Act
        actualResult.updateHit();
        // Assert
        assertThat(actualResult.getHit()).isNotZero();
        assertThat(actualResult.getHit()).isEqualTo(1L);
    }

}