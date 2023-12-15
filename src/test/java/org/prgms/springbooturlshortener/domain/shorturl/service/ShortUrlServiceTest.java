package org.prgms.springbooturlshortener.domain.shorturl.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbooturlshortener.domain.shorturl.ShortUrl;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlErrorCode;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlException;
import org.prgms.springbooturlshortener.domain.shorturl.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ShortUrlServiceTest {
    @Autowired
    private ShortUrlService shortUrlService;
    @Autowired
    private ShortUrlRepository shortUrlRepository;

    private String preSavedOriginalUrl = "youtube.com";
    private String preSavedTransformedUrl;

    @BeforeEach
    void setUp() {
        preSavedTransformedUrl = shortUrlService.saveOriginalUrl(preSavedOriginalUrl);
    }

    @AfterEach
    void tearDown() {
        shortUrlRepository.deleteAll();
    }

    @Test
    void saveOriginalUrl() {
        String originalUrl = "https://naver.com";
        String transformedUrl = shortUrlService.saveOriginalUrl(originalUrl);

        ShortUrl savedShortUrl = shortUrlRepository.findById(transformedUrl)
                .orElseThrow(() -> new UrlException(UrlErrorCode.ORIGINAL_URL_NOT_FOUND));

        assertThat(savedShortUrl.getTransformedUrl()).isEqualTo(transformedUrl);
    }

    @Test
    void getOriginalUrl() {
        String originalUrl = shortUrlService.getOriginalUrl(preSavedTransformedUrl);

        assertThat(originalUrl).isEqualTo(preSavedOriginalUrl);
    }
}
