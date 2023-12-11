package com.programmers.springbooturlshortener.domain.application;

import com.programmers.springbooturlshortener.domain.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.domain.dto.UrlServiceResponseDto;
import com.programmers.springbooturlshortener.domain.entity.EncodeType;
import com.programmers.springbooturlshortener.domain.entity.LongUrl;
import com.programmers.springbooturlshortener.domain.entity.Url;
import com.programmers.springbooturlshortener.domain.exception.NoSuchUrlFoundException;
import com.programmers.springbooturlshortener.domain.infrastructure.UrlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.programmers.springbooturlshortener.common.exception.ExceptionMessage.NO_SUCH_URL_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Test UrlService")
@Transactional
@Testcontainers
class UrlServiceTest {

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UrlService urlService;

    private final String longUrlValue = "https://google.com";
    private final LongUrl longUrl = LongUrl.from(longUrlValue);
    private final EncodeType encodeType = EncodeType.BASE62;

    @DisplayName("Test create Short URL")
    @Test
    void testCreateShortUrl() {
        // Arrange
        UrlServiceRequestDto requestDto = UrlServiceRequestDto.builder()
                .longUrl(longUrl)
                .encodeType(encodeType)
                .build();
        // Act
        UrlServiceResponseDto actualResult = urlService.createShortUrl(requestDto);
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getLongUrl().getValue()).isEqualTo(longUrlValue);
        assertThat(actualResult.getShortUrl()).isNotNull();
        assertThat(actualResult.getHit()).isZero();
    }

    @DisplayName("Test create Short URL if already Have")
    @Test
    void testCreateShortUrlDuplicated() {
        // Arrange
        Url expectedUrl = urlRepository.save(Url.builder()
                .longUrl(longUrlValue)
                .encodeType(encodeType)
                .shortUrl(encodeType.encode(longUrlValue))
                .build());
        UrlServiceRequestDto requestDto = UrlServiceRequestDto.builder()
                .longUrl(longUrl)
                .encodeType(encodeType)
                .build();
        // Act
        UrlServiceResponseDto actualResult = urlService.createShortUrl(requestDto);
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getLongUrl().getValue()).isEqualTo(expectedUrl.getLongUrl());
        assertThat(actualResult.getShortUrl()).isEqualTo(expectedUrl.getShortUrl());
        assertThat(actualResult.getHit()).isZero();
    }

    @DisplayName("Test find Short URL Success")
    @Test
    void testFindShortUrlSuccess(){
        // Arrange
        Url expectedUrl = urlRepository.save(Url.builder()
                .longUrl(longUrlValue)
                .encodeType(encodeType)
                .shortUrl(encodeType.encode(longUrlValue))
                .build());
        UrlServiceRequestDto requestDto = UrlServiceRequestDto.builder()
                .shortUrl(expectedUrl.getShortUrl())
                .build();
        // Act
        UrlServiceResponseDto actualResult = urlService.findLongUrl(requestDto);
        // Assert
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getLongUrl().getValue()).isEqualTo(expectedUrl.getLongUrl());
        assertThat(actualResult.getShortUrl()).isEqualTo(expectedUrl.getShortUrl());
        assertThat(actualResult.getHit()).isNotZero();
    }

    @DisplayName("Test find Short URL Fail")
    @Test
    void testFindShortUrlFailWhenNoSuchShortUrl(){
        // Arrange
        UrlServiceRequestDto requestDto = UrlServiceRequestDto.builder()
                .shortUrl(encodeType.encode(longUrlValue))
                .build();
        // Act & Assert
        Throwable actualResult = assertThrows(NoSuchUrlFoundException.class, () -> urlService.findLongUrl(requestDto));
        assertThat(actualResult).hasMessage(NO_SUCH_URL_FOUND.getValue());
    }
}