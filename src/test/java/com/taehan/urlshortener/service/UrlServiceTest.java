package com.taehan.urlshortener.service;

import com.taehan.urlshortener.dto.UrlRequestDto;
import com.taehan.urlshortener.model.AlgorithmType;

import com.taehan.urlshortener.model.Url;
import com.taehan.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({UrlService.class})
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository repository;

    @Test
    @DisplayName("Url 저장 테스트")
    void testSave() {
        UrlRequestDto requestDto = new UrlRequestDto("naver.com", AlgorithmType.BASE62);
        long urlId = urlService.save(requestDto);

        Url savedUrl = repository.findById(urlId).get();

        assertThat(savedUrl.getUrl()).isEqualTo(requestDto.getUrl());
    }

    @Test
    @DisplayName("잘못된 Url은 저장 실패")
    void testInvalidUrlSave() {
        UrlRequestDto requestDto = new UrlRequestDto("naver", AlgorithmType.BASE62);

        assertThrows(ConstraintViolationException.class,
                () -> {
                    urlService.save(requestDto);
                });

    }

    @Test
    @DisplayName("Short URL로 원본 URL 요청 테스트")
    void testGetOriginalUrl() {
        UrlRequestDto requestDto = new UrlRequestDto("naver.com", AlgorithmType.BASE62);
        long urlId = urlService.save(requestDto);
        Url savedUrl = repository.findById(urlId).get();
        String shortUrl = savedUrl.getShortUrl();

        String originalUrl = urlService.getOriginalUrl(shortUrl);

        assertThat(originalUrl).isEqualTo(savedUrl.getUrl());
    }

    @Test
    @DisplayName("Short Url로 요청 시 Url의 count가 늘어남 테스트")
    void testGetOriginalUrl_AddCount_카운트_증가() {
        UrlRequestDto requestDto = new UrlRequestDto("naver.com", AlgorithmType.BASE62);
        long urlId = urlService.save(requestDto);
        Url savedUrl = repository.findById(urlId).get();
        String shortUrl = savedUrl.getShortUrl();

        urlService.getOriginalUrl(shortUrl);
        urlService.getOriginalUrl(shortUrl);

        assertThat(savedUrl.getCount()).isEqualTo(2);
    }

}