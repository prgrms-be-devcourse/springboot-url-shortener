package com.programmers.springbooturlshortener.integration;

import com.programmers.springbooturlshortener.domain.url.Url;
import com.programmers.springbooturlshortener.domain.url.UrlRepository;
import com.programmers.springbooturlshortener.domain.url.UrlService;
import com.programmers.springbooturlshortener.domain.url.dto.UrlResponseDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class IntegrationTest {

    @Autowired
    UrlService urlService;

    @Autowired
    UrlRepository urlRepository;

    @Test
    @DisplayName("새로운 ShortURL 생성 성공")
    void createNewShortUrlSuccess() {
        // given
        String originUrl = "https//google.com";
        String algorithm = "Base62";
        UrlServiceRequestDto urlServiceRequestDto = new UrlServiceRequestDto(originUrl, algorithm);

        String shortUrl = "AAAAAAB";
        UrlResponseDto result = new UrlResponseDto(originUrl, shortUrl, 1L);

        // when
        UrlResponseDto urlResponseDto = urlService.createShortUrl(urlServiceRequestDto);

        // then
        assertThat(urlResponseDto).isEqualTo(result);
    }

    @Test
    @DisplayName("이미 존재하는 URL인 경우 DB 조회 후 RequestCount 증가 후 반환 성공")
    void createOldShortUrlIncreaseRequestCountSuccess() {
        // given
        String originUrl = "google.com";
        String algorithm = "Base62";
        UrlServiceRequestDto urlServiceRequestDto = new UrlServiceRequestDto(originUrl, algorithm);

        Url url = new Url(algorithm, originUrl, 1L);
        String shortUrl = "AAAAAAB";
        url.setShortUrl(shortUrl);
        urlRepository.save(url);

        UrlResponseDto result = new UrlResponseDto(originUrl, shortUrl, url.getRequestCount() + 1);

        // when
        UrlResponseDto urlResponseDto = urlService.createShortUrl(urlServiceRequestDto);

        // then
        assertThat(urlResponseDto).isEqualTo(result);
    }

    @Test
    @DisplayName("DB 에 저장되어 있는 ShortURL 조회 후 UrlResponseDto 반환 성공")
    void getOriginUrlSuccess() {
        // given
        String originUrl = "google.com";
        String shortUrl = "AAAAAAB";
        Long requestCount = 1L;
        UrlResponseDto result = new UrlResponseDto(originUrl, shortUrl, requestCount);

        String algorithm = "Base62";
        Url url = new Url(algorithm, originUrl, requestCount);
        url.setShortUrl(shortUrl);
        urlRepository.save(url);

        // when
        UrlResponseDto urlResponseDto = urlService.getOriginUrl(shortUrl);

        // then
        assertThat(urlResponseDto).isEqualTo(result);
    }

    @Test
    @DisplayName("DB 에 존재하지 않는 ShortURL 조회 시 EntityNotFoundException 발생")
    void getOriginUrlWhenNotExistsUrlFail() {
        // given
        String shortUrl = "AAAAAAB";

        // when, then
        assertThrows(EntityNotFoundException.class, () -> urlService.getOriginUrl(shortUrl));
    }
}
