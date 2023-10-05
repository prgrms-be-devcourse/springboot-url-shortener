package com.progms.shorturl.domain;

import com.progms.shorturl.application.UrlGenerationService;
import com.progms.shorturl.entity.ShortUrl;
import com.progms.shorturl.repository.ShortUrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ShortUrlRepositoryTest {

    @Autowired
    ShortUrlRepository shortUrlRepository;

    UrlGenerationService urlGenerationService = new UrlGenerationService();

    ShortUrl testShortUrl;

    @BeforeEach
    void init() {
        testShortUrl = ShortUrl.createShortUrl("https://test.com/testtesttesttesttesttesttesttest");
    }

    @DisplayName("DB connect 성공 테스트")
    @Test
    void repository_connect_success() {
        assertThat(shortUrlRepository).isNotNull();
    }

    @DisplayName("Shroturl 저장 성공 테스트")
    @Test
    void shortUrl_save_success() {
        // given, when
        shortUrlRepository.save(testShortUrl);

        // then
        assertThat(testShortUrl.getShortId()).isNotNull();
    }
}
