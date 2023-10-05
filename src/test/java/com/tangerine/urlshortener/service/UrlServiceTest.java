package com.tangerine.urlshortener.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.junit.jupiter.api.Assertions.*;

import com.tangerine.urlshortener.controller.UrlMappingResult;
import com.tangerine.urlshortener.model.UrlMapping;
import com.tangerine.urlshortener.model.vo.Algorithm;
import com.tangerine.urlshortener.model.vo.OriginUrl;
import com.tangerine.urlshortener.model.vo.RequestCount;
import com.tangerine.urlshortener.model.vo.ShortUrl;
import com.tangerine.urlshortener.repository.UrlMappingJpaRepository;
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
    @DisplayName("원본 URL로 매핑 정보를 조회한다.")
    void findMapping_Success() {
        // Given
        OriginUrl originUrl = new OriginUrl("http://tangerine.com/tangerine-test");
        UrlMapping urlMapping = urlMappingJpaRepository.save(
                new UrlMapping(
                        originUrl,
                        new ShortUrl("123"),
                        new Algorithm("알고리즘"),
                        new RequestCount(0)
                )
        );

        // When
        UrlMappingResult result = urlService.findMapping(originUrl);

        // Then
        assertThat(result.shortUrl()).isEqualTo(urlMapping.getShortUrlText());
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