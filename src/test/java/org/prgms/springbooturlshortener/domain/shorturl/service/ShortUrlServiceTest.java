package org.prgms.springbooturlshortener.domain.shorturl.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.springbooturlshortener.domain.shorturl.ShortUrl;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlErrorCode;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlException;
import org.prgms.springbooturlshortener.domain.shorturl.repository.ShortUrlRepository;
import org.prgms.springbooturlshortener.domain.shorturl.service.dto.TransformedShortUrlDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class ShortUrlServiceTest {
    @Autowired
    private ShortUrlService shortUrlService;
    @Autowired
    private ShortUrlRepository shortUrlRepository;

    private final String preSavedOriginalUrl = "youtube.com";
    private TransformedShortUrlDto preSavedTransformedShortUrlDto;

    @BeforeEach
    void setUp() {
        preSavedTransformedShortUrlDto = shortUrlService.saveOriginalUrl(preSavedOriginalUrl);
    }

    @AfterEach
    void tearDown() {
        shortUrlRepository.deleteAll();
    }

    @Test
    @DisplayName("새로운 URL을 짧은 URL로 변환할 수 있다.")
    void saveNewOriginalUrl() {
        String originalUrl = "https://naver.com";
        TransformedShortUrlDto transformedUrl = shortUrlService.saveOriginalUrl(originalUrl);

        ShortUrl savedShortUrl = shortUrlRepository.findById(transformedUrl.shortUrl())
                .orElseThrow(() -> new UrlException(UrlErrorCode.ORIGINAL_URL_NOT_FOUND));

        assertThat(savedShortUrl.getTransformedUrl()).isEqualTo(transformedUrl.shortUrl());
    }

    @Test
    @DisplayName("이미 존재하는 URL이면 짧은 URL을 생성 없이 즉시 반환한다.")
    void getAlreadyExistingOriginalUrl() {
        TransformedShortUrlDto transformedShortUrlDto = shortUrlService.saveOriginalUrl(preSavedOriginalUrl);

        assertThat(transformedShortUrlDto.shortUrl()).isEqualTo(preSavedTransformedShortUrlDto.shortUrl());
    }

    @Test
    @DisplayName("기존 URL을 가져올 수 있다.")
    void getOriginalUrl() {
        String originalUrl = shortUrlService.getOriginalUrl(preSavedTransformedShortUrlDto.shortUrl());

        assertThat(originalUrl).isEqualTo(preSavedOriginalUrl);
    }

    @Test
    @DisplayName("짧은 URL을 이용해 기존 URL을 가져온다면 방문 횟수와 마지막 방문 일시가 수정되어야 한다.")
    void updateLastVisitTimeAndVisitCountWhenFindUrl() {
        shortUrlService.getOriginalUrl(preSavedTransformedShortUrlDto.shortUrl());

        Optional<ShortUrl> shortUrl = shortUrlRepository.findById(preSavedTransformedShortUrlDto.shortUrl());

        assertThat(shortUrl.isPresent()).isTrue();

        TransformedShortUrlDto urlInfoAfterVisit = shortUrl.get().toTransformedShortUrlDto();

        assertThat(urlInfoAfterVisit.lastVisitTime()).isNotEqualTo(preSavedTransformedShortUrlDto.lastVisitTime());
        assertThat(urlInfoAfterVisit.visitCount()).isEqualTo(preSavedTransformedShortUrlDto.visitCount() + 1);

        log.info("before lastVisitTime:{}, after lastVisitTime:{}", preSavedTransformedShortUrlDto.lastVisitTime(), urlInfoAfterVisit.lastVisitTime());
    }
}
