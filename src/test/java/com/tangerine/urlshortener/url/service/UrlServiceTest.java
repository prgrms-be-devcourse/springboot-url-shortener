package com.tangerine.urlshortener.url.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.tangerine.urlshortener.url.model.UrlMapping;
import com.tangerine.urlshortener.url.model.vo.Algorithm;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.model.vo.RequestCount;
import com.tangerine.urlshortener.url.model.vo.ShortUrl;
import com.tangerine.urlshortener.url.repository.UrlMappingJpaRepository;
import com.tangerine.urlshortener.url.service.dto.ShortenParam;
import com.tangerine.urlshortener.url.service.dto.UrlMappingResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlMappingJpaRepository urlMappingJpaRepository;

    @Test
    @DisplayName("원본 URL로 매핑 정보를 등록한다.")
    void createShortUrl_Success() {
        // Given
        ShortenParam param = new ShortenParam(
                new OriginUrl("http://tangerine.com/tangerine-test"),
                Algorithm.BASE62
        );

        // When
        UrlMappingResult result = urlService.createShortUrl(param);

        // Then
        String expected = param.algorithm().encode(result.id());
        assertThat(result.shortUrl()).isEqualTo(new ShortUrl(expected));
    }

    @Test
    @DisplayName("원본 URL로 매핑 정보를 조회한다.")
    void findMapping_Success() {
        // Given
        OriginUrl originUrl = new OriginUrl("http://tangerine.com/tangerine-test");
        UrlMapping urlMapping = urlMappingJpaRepository.save(
                new UrlMapping(
                        originUrl,
                        new ShortUrl("123"),
                        Algorithm.BASE62,
                        new RequestCount(0)
                )
        );

        // When
        UrlMappingResult result = urlService.findMapping(originUrl);

        // Then
        assertThat(result.shortUrl()).isEqualTo(urlMapping.getShortUrl());
    }

    @Test
    @DisplayName("매핑 정보가 없는 원본 URL로 매핑 정보 조회 시 실패한다.")
    void findMapping_Fail() {
        // Given
        OriginUrl originUrl = new OriginUrl("http://tangerine.com/tangerine-test");

        // When
        Exception exception = catchException(() -> urlService.findMapping(originUrl));

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

}