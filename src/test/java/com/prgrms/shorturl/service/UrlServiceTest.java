package com.prgrms.shorturl.service;

import com.prgrms.shorturl.domain.Url;
import com.prgrms.shorturl.dto.UrlRequest;
import com.prgrms.shorturl.dto.UrlResponse;
import com.prgrms.shorturl.exception.NoSuchOriginalUrlException;
import com.prgrms.shorturl.repository.UrlRepository;
import com.prgrms.shorturl.utils.HashAlgorithm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UrlServiceTest {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlService urlService;


    @AfterEach
    void afterEach() {
        urlRepository.deleteAll();
    }

    @Test
    @DisplayName("존재하는 original url인지 확인 - 존재하지 않을 경우")
    void checkIsPresent() {
        UrlRequest req = UrlRequest.builder()
                .originalUrl("www.naver.com")
                .hashAlgorithm(HashAlgorithm.SHA_256)
                .build();
        boolean isPresent = urlService.isPresent(req);
        assertThat(isPresent).isFalse();
    }

    @Test
    @DisplayName("존재하는 original url인지 확인 - 존재할 경우")
    void checkIsNotPresent() {
        urlService.save("www.naver.com", HashAlgorithm.SHA_256);
        UrlRequest req = UrlRequest.builder()
                .originalUrl("www.naver.com")
                .hashAlgorithm(HashAlgorithm.SHA_256)
                .build();
        boolean isPresent = urlService.isPresent(req);
        assertThat(isPresent).isTrue();
    }

    @Test
    @DisplayName("shorturl 얻기 - SHA-256")
    void getShortUrlSha256() {
        UrlRequest req = UrlRequest.builder()
                .originalUrl("www.naver.com")
                .hashAlgorithm(HashAlgorithm.SHA_256)
                .build();
        UrlResponse shortUrl = urlService.getShortUrl(req);
        assertThat(shortUrl.originalUrl()).isEqualTo("www.naver.com");
    }

    @Test
    @DisplayName("shorturl 얻기 - Sequence")
    void getShortUrlSequence() {
        UrlRequest req = UrlRequest.builder()
                .originalUrl("www.naver.com")
                .hashAlgorithm(HashAlgorithm.SEQUENCE)
                .build();
        UrlResponse shortUrl = urlService.getShortUrl(req);
        assertThat(shortUrl.originalUrl()).isEqualTo("www.naver.com");
    }

    @Test
    @DisplayName("originalUrl 얻기")
    void getOriginalUrl() {
        UrlRequest req = UrlRequest.builder()
                .originalUrl("www.naver.com")
                .hashAlgorithm(HashAlgorithm.SEQUENCE)
                .build();
        UrlResponse shortUrl = urlService.getShortUrl(req);
        String originalUrl = urlService.getOriginalUrl(shortUrl.shortUrl());
        assertThat(originalUrl).isEqualTo("www.naver.com");
    }

    @Test
    @DisplayName("originalUrl 얻기 - 실패")
    void getOriginalUrlFail() {
        assertThatThrownBy(() -> {
            urlService.getOriginalUrl("VvY2d");
        })
                .isExactlyInstanceOf(NoSuchOriginalUrlException.class)
                .hasMessage("매칭되는 주소가 없습니다.");
    }
}