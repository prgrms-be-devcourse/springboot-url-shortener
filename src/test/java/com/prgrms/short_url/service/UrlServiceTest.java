package com.prgrms.short_url.service;

import com.prgrms.short_url.domain.Url;
import com.prgrms.short_url.dto.UrlDto;
import com.prgrms.short_url.exception.NoConnectUrlException;
import com.prgrms.short_url.exception.NotFoundException;
import com.prgrms.short_url.repository.UrlRepository;
import com.prgrms.short_url.validation.UrlValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("test")
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlValidation urlValidation;

    @BeforeEach
    void setup() {

    }

    @Test
    @DisplayName("short url 생성 및 저장 테스트")
    void createShortUrlTest() {
        // Given
        String originUrl = "http://naver.com";
        UrlDto urlDto = new UrlDto(originUrl);
        originUrl = urlValidation.httpValid(originUrl);

        // When
        String shortUrl = urlService.createShortUrl(urlDto);

        // Then
        Url url = urlRepository.getUrlByShortUrl(shortUrl)
                .orElseThrow(NotFoundException::new);
        List<Url> all = urlRepository.findAll();

        assertThat(url.getOriginalUrl().equals(originUrl), is(true));
        assertThat(all, hasSize(1));
    }

    @Test
    @DisplayName("short url 생성 및 저장 실패 테스트")
    void createShortUrlFailTest() {
        // Given
        String originUrl = "http://naver";
        UrlDto urlDto = new UrlDto(originUrl);
        String url = urlValidation.httpValid(originUrl);

        // When

        // Then
        assertThrows(NoConnectUrlException.class, () ->  urlService.createShortUrl(urlDto));
        assertThrows(NotFoundException.class, () ->  urlService.getShortUrlByOriginalUrl(url));
    }

    @Test
    @DisplayName("기존 short url있는 경우 테스트")
    void getShortUrlTest() {
        // Given
        String url1 = "http://naver.com";
        String url2 = "https://www.naver.com";
        UrlDto urlDto = new UrlDto(url1);

        // When
        String shortUrl1 = urlService.createShortUrl(urlDto);
        urlDto = new UrlDto(url2);
        String shortUrl2 = urlService.createShortUrl(urlDto);

        // Then
        assertThat(shortUrl1.equals(shortUrl2), is(true));
        List<Url> all = urlRepository.findAll();
        assertThat(all, hasSize(1));

    }

    @Test
    @DisplayName("리다이렉트를 위한 shortUrl을 통해 원래의 url 찾는 테스트")
    void getOriginUrlTest() {
        // Given
        String url = "http://naver.com";
        UrlDto urlDto = new UrlDto(url);
        String originUrl = urlValidation.httpValid(url);

        // When
        urlService.createShortUrl(urlDto);
        Url findUrl = urlRepository.getUrlByOriginalUrl(originUrl)
                .orElseThrow(NotFoundException::new);
        String shortUrl = findUrl.getShortUrl();

        // Then
        String redirectUrl = urlService.redirect(shortUrl.substring(22));
        assertThat(originUrl.equals(redirectUrl), is(true));
    }

    @Test
    @DisplayName("기존 shortUrl을 통해 리다이렉트가 안되는 경우 - 찾는 원본 url이 없는 경우")
    void getOriginUrlFailTest() {
        // Given
        String url = "http://naver.com";
        UrlDto urlDto = new UrlDto(url);
        String originUrl = urlValidation.httpValid(url);

        // When
        urlService.createShortUrl(urlDto);
        Url findUrl = urlRepository.getUrlByOriginalUrl(originUrl)
                .orElseThrow(NotFoundException::new);
        String shortUrl = findUrl.getShortUrl() + "t";

        // Then
        assertThrows(NotFoundException.class, () -> urlService.redirect(shortUrl));
    }
}