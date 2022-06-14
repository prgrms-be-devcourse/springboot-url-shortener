package com.urlshortener.url.service;

import com.urlshortener.url.model.dto.CreateRequest;
import com.urlshortener.url.model.dto.CreateResponse;
import com.urlshortener.url.repository.UrlRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@DisplayName("Simple Url Service 테스트")
class SimpleUrlServiceTest {
    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        urlRepository.deleteAll();
    }

    @Nested
    @Order(1)
    @DisplayName("register() : Short URL을 생성하여 등록합니다.")
    class RegisterTest {
        List<String> originUrls;

        @BeforeEach
        void setUp() {
            originUrls = Arrays.asList("https://www.daum.net/", "https://www.naver.com/", "https://www.google.com/");
        }

        @Test
        @DisplayName("성공 : 서로 다른 URL 값이 등록 시도될 경우")
        void successAsUniqueUrls() {
            // Given, When
            AtomicInteger totalCount = new AtomicInteger();
            originUrls.forEach(url -> {
                CreateResponse response = urlService.register(new CreateRequest(url));
                if (Objects.nonNull(response)){
                    totalCount.getAndIncrement();
                }
            });
            // Then
            assertThat(totalCount.get()).isEqualTo(originUrls.size());
        }

        @Test
        @DisplayName("성공 : 서로 같은 URL 값이 등록 시도될 경우")
        void successAsSameUrls() {
            // Given
            Map<String, String> originShortUrls = new HashMap<>();
            originUrls.forEach(origin -> {
                CreateResponse response = urlService.register(new CreateRequest(origin));
                originShortUrls.put(origin, response.getShortUrl());
            });
            // When, Then
            originUrls.forEach(origin -> {
                CreateResponse response = urlService.register(new CreateRequest(origin));
                assertThat(response.getShortUrl()).isEqualTo(originShortUrls.get(origin));
            });
        }
    }

    @Nested
    @Order(2)
    @DisplayName("findOne() : Short URL 조회 테스트")
    class FindOneTest {
        Set<String> originUrls;
        Map<String, String> shortOriginUrls;

        @BeforeEach
        void setUp() {
            originUrls = new HashSet<>(
                Arrays.asList("https://www.daum.net", "https://www.naver.com", "https://www.google.com"));
            shortOriginUrls = new HashMap<>();
        }

        @Test
        @DisplayName("성공 : 존재하는 URL 값을 조회하는 경우")
        void success() {
            // Given
            originUrls.forEach(s -> {
                CreateResponse response = urlService.register(new CreateRequest(s));
                shortOriginUrls.put(response.getShortUrl(), s);
            });
            // When, Then
            shortOriginUrls.keySet().forEach(
                shortUrl -> {
                    String expectedUrl = shortOriginUrls.get(shortUrl);
                    String actualUrl = urlService.findOne(shortUrl);
                    assertThat(actualUrl).isEqualTo(expectedUrl);
                }
            );
        }

        @Test
        @DisplayName("실패 : 존재하지 않는 Url 값을 조회하는 경우")
        void fail() {
            // Given, When
            Throwable response = catchThrowable(() -> urlService.findOne("0o0o0o"));
            // Then
            assertThat(response).isInstanceOf(NullPointerException.class);
        }
    }
}