package com.springboot.springbooturlshortner.service;

import com.springboot.springbooturlshortner.domain.Url;
import com.springboot.springbooturlshortner.exception.UrlException;
import com.springboot.springbooturlshortner.exception.UrlExceptionCode;
import com.springboot.springbooturlshortner.repository.UrlRepository;
import com.springboot.springbooturlshortner.util.Base62Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;
    @InjectMocks
    private Base62Util base62Util;
    @Mock
    private UrlRepository urlRepository;
    @Value("${spring.start-url}")
    private String startUrl;

    @BeforeEach
    void setUp() {
        urlService = new UrlService(urlRepository, base62Util);
    }

    @Test
    @DisplayName("단축 url 생성 성공")
    void Success_CreateShortenUrl() {
        // given
        String originUrl = "https://test-url";
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto(originUrl);
        Url url = shortenUrlRequestDto.toUrlEntity();
        url.setId(10000L);
        String expectedShortenUrl = startUrl + "/" + base62Util.encoding(url.getId());
        when(urlRepository.save(any(Url.class))).thenReturn(url);

        // when
        String resultShortenUrl = urlService.createShortenUrl(shortenUrlRequestDto);

        // then
        Assertions.assertEquals(expectedShortenUrl, resultShortenUrl);
    }

    @Test
    @DisplayName("단축 url 생성 실패 - 유효하지 않은 기존 url")
    void Fail_CreateShortenUrl_With_InvalidOriginUrl() {
        // given
        String originUrl = "htt://test-url";
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto(originUrl);

        // when
        UrlException e = Assertions.assertThrows(UrlException.class, () -> urlService.createShortenUrl(shortenUrlRequestDto));

        // then
        Assertions.assertEquals(UrlExceptionCode.INVALID_ORIGIN_URL.getMessage(), e.getMessage());
    }

    @Test
    @DisplayName("기존 url 반환 성공")
    void Success_GetOriginUrl() {
        // given
        String expectedOriginUrl = "https://test-url";
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto(expectedOriginUrl);
        Url url = shortenUrlRequestDto.toUrlEntity();
        url.setId(10000L);
        String shortenUrl = startUrl + "/" + base62Util.encoding(url.getId());
        when(urlRepository.findById(any(Long.class))).thenReturn(Optional.of(url));

        // when
        String resultOriginUrl = urlService.getOriginUrl(shortenUrl);

        // then
        Assertions.assertEquals(expectedOriginUrl, resultOriginUrl);
    }

    @Test
    @DisplayName("단축 url 생성 실패 - 존재하지 않는 단축 url")
    void Fail_GetOriginUrl_With_NotExistingShortenUrl() {
        // given
        String expectedOriginUrl = "https://test-url";
        ShortenUrlRequestDto shortenUrlRequestDto = new ShortenUrlRequestDto(expectedOriginUrl);
        Url url = shortenUrlRequestDto.toUrlEntity();
        url.setId(10000L);
        String shortenUrl = startUrl + "/" + base62Util.encoding(url.getId()) + "test";
        when(urlRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // when
        UrlException e = Assertions.assertThrows(UrlException.class, () -> urlService.getOriginUrl(shortenUrl));

        // then
        Assertions.assertEquals(UrlExceptionCode.NOT_FOUND.getMessage(), e.getMessage());
    }

}