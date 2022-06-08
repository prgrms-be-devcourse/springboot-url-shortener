package com.taehan.urlshortener.service;

import com.taehan.urlshortener.dto.UrlRequestDto;
import com.taehan.urlshortener.model.AlgorithmType;
import com.taehan.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({UrlService.class})
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository repository;

    @Test
    void testConvertShortUrl() {
        UrlRequestDto requestDto = new UrlRequestDto("naver.com", AlgorithmType.BASE62);
        String shortUrl = urlService.convertToShortUrl(requestDto);

        assertThat(shortUrl).isEqualTo("base62");
    }

}