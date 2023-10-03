package com.programmers.urlshortener.url.repository;

import static com.programmers.urlshortener.url.domain.Algorithm.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.urlshortener.url.domain.Url;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UrlRepositoryTest {

    @Autowired
    UrlRepository urlRepository;

    private static Url url;

    @BeforeAll
    static void setUp() {
        // given
        url = Url.builder()
            .originalUrl("https://www.naver.com")
            .algorithm(BASE62)
            .ip("0.0.0.0")
            .build();
    }

    @Test
    @DisplayName("Url을 생성하고 조회할 수 있다.")
    @Transactional
    void createUrl_And_Save() {
        // when
        Url savedUrl = urlRepository.save(url);
        savedUrl.convertToShortUrl();

        Url getUrl = urlRepository.findByShortUrl(savedUrl.getShortUrl()).get();

        // then
        assertThat(getUrl.getId()).isEqualTo(100000L);
    }
}
