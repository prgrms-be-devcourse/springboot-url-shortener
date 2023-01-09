package com.programmers.springbooturlshortener;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.programmers.springbooturlshortener.domain.url.Url;
import com.programmers.springbooturlshortener.domain.url.UrlRepository;
import com.programmers.springbooturlshortener.domain.url.UrlService;
import com.programmers.springbooturlshortener.domain.url.UrlServiceFacade;
import com.programmers.springbooturlshortener.domain.url.dto.UrlResponseDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;

@SpringBootTest
class IntegrationTest {

    @Autowired
    UrlService urlService;

    @Autowired
    UrlServiceFacade urlServiceFacade;

    @Autowired
    UrlRepository urlRepository;

    @Test
    @DisplayName("새로운 ShortURL 생성과 조회에 성공")
    void createNewShortUrlSuccess() {
        // given
        String originUrl = "https//google.com";
        String algorithm = "Base62";
        UrlServiceRequestDto urlServiceRequestDto = new UrlServiceRequestDto(originUrl, algorithm);

        // when
        UrlResponseDto savedUrl = urlService.createShortUrl(urlServiceRequestDto);
        Url findUrl = urlRepository.findByOriginUrl(savedUrl.originUrl()).get();

        // then
        assertThat(findUrl).hasFieldOrPropertyWithValue("originUrl", savedUrl.originUrl())
            .hasFieldOrPropertyWithValue("shortUrl", savedUrl.shortUrl())
            .hasFieldOrPropertyWithValue("requestCount", savedUrl.requestCount());
    }

    @Test
    @DisplayName("이미 존재하는 URL인 경우 DB 조회 후 RequestCount 증가 후 반환 성공")
    void createOldShortUrlIncreaseRequestCountSuccess() {
        // given
        String originUrl = "google.com";
        String algorithm = "Base62";
        UrlServiceRequestDto urlServiceRequestDto = new UrlServiceRequestDto(originUrl, algorithm);

        UrlResponseDto firstRequestUrl = urlService.createShortUrl(urlServiceRequestDto);

        // when
        UrlResponseDto lastRequestDto = urlService.createShortUrl(urlServiceRequestDto);

        // then
        assertThat(lastRequestDto).hasFieldOrPropertyWithValue("originUrl", firstRequestUrl.originUrl())
            .hasFieldOrPropertyWithValue("shortUrl", firstRequestUrl.shortUrl())
            .hasFieldOrPropertyWithValue("requestCount", firstRequestUrl.requestCount() + 1);
    }

    @Test
    @DisplayName("DB 에 저장되어 있는 ShortURL 조회 후 UrlResponseDto 반환 성공")
    void getOriginUrlSuccess() {
        // given
        String originUrl = "google.com";
        String algorithm = "Base62";
        UrlServiceRequestDto urlServiceRequestDto = new UrlServiceRequestDto(originUrl, algorithm);

        UrlResponseDto savedUrl = urlService.createShortUrl(urlServiceRequestDto);

        // when
        UrlResponseDto findUrl = urlService.getOriginUrl(savedUrl.shortUrl());

        // then
        assertThat(findUrl).isEqualTo(savedUrl);
    }

    @Test
    @DisplayName("DB 에 존재하지 않는 ShortURL 조회 시 EntityNotFoundException 발생")
    void getOriginUrlWhenNotExistsUrlFail() {
        // given
        String notInDbShortURL = "AAAAAAA";

        // when, then
        assertThrows(EntityNotFoundException.class, () -> urlService.getOriginUrl(notInDbShortURL));
    }

    @Test
    @DisplayName("50명의 사용자가 동시에 같은 URL을 요청할 경우 확인")
    void createUrlWhenFiftyUsersRequestSameUrlAtSameTimeSuccess() throws InterruptedException {
        // given
        String savedOriginUrl = "google.com";
        String originUrl = "https://google.com";
        String algorithm = "Base62";
        UrlServiceRequestDto urlServiceRequestDto = new UrlServiceRequestDto(originUrl, algorithm);

        int threadCount = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    urlServiceFacade.createShortUrl(urlServiceRequestDto);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Url url = urlRepository.findByOriginUrl(savedOriginUrl).get();

        // then
        Assertions.assertThat(url.getRequestCount()).isEqualTo(50);
    }
}
