package com.tangerine.urlshortener.url.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.tangerine.urlshortener.url.model.UrlMapping;
import com.tangerine.urlshortener.url.model.vo.Algorithm;
import com.tangerine.urlshortener.url.model.vo.OriginUrl;
import com.tangerine.urlshortener.url.model.vo.ShortUrl;
import com.tangerine.urlshortener.url.repository.UrlMappingJpaRepository;
import com.tangerine.urlshortener.url.service.dto.ShortenParam;
import com.tangerine.urlshortener.url.service.dto.UrlMappingResult;
import com.tangerine.urlshortener.url.service.dto.UrlMappingResults;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:/application-test.yaml")
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
    @DisplayName("매핑 정보 없는 단축 URL 조회 시 실패한다.")
    void findOriginUrl_Fail() {
        // Given
        ShortUrl shortUrl = new ShortUrl("YSDKFasf");

        // When
        Exception exception = catchException(() -> urlService.findOriginUrl(shortUrl));

        // Then
        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("모든 매핑 정보를 조회한다.")
    void findAllMappings_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 20);
        OriginUrl originUrl = new OriginUrl("http://tangerine.com/tangerine-test");
        UrlMapping urlMapping = urlMappingJpaRepository.save(
                new UrlMapping(
                        originUrl,
                        Algorithm.BASE62));
        urlMapping.setShortUrl(new ShortUrl("123"));

        // When
        UrlMappingResults mappingResults = urlService.findAllMappings(pageable);

        // Then
        assertThat(mappingResults.results()).isNotEmpty();

        List<UrlMappingResult> content = mappingResults.results().getContent();

        assertThat(content.get(0).shortUrl()).isEqualTo(urlMapping.getShortUrl());
    }

}