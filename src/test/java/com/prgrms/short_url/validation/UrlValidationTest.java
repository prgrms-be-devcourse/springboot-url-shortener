package com.prgrms.short_url.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@DisplayName("URL Validation 테스트")
class UrlValidationTest {

    @Autowired
    private UrlValidation urlValidation;

    private List<String> urlList;

    @BeforeEach()
    void setup() {
        urlList = new ArrayList<>();
    }

    @Test
    @DisplayName("http(s):// 형식으로 변경 확인")
    void convertUrlTest() {
        // Given
        String pattern = "https://www.";

        // When
        urlList.add("http://www.google.co.kr");
        urlList.add("https//www.naver.com");
        urlList.add("https:/www.youtube.com");
        urlList.add("https:youtube.com");
        urlList.add("http:youtube.com");
        urlList.add("www.youtube.com");
        List<String> collect = urlList.stream()
                .map(url -> urlValidation.httpValid(url))
                .filter(url -> url.contains(pattern))
                .collect(Collectors.toList());

        // Then
        assertThat(collect, hasSize(urlList.size()));

    }

    @Test
    @DisplayName("올바른 URL 연결 테스트")
    void collectUrlTest() {
        // Given

        // When
        urlList.add("https://www.google.co.kr");
        urlList.add("https://www.naver.com");
        urlList.add("https://www.youtube.com");
        urlList.add("https://youtube.com");

        // Then
        for (String url : urlList) {
            boolean b = urlValidation.connectValid(url);
            assertThat(b, is(true));
        }
    }

    @Test
    @DisplayName("잘못된 URL 연결 테스트")
    void wrongUrlTest() {
        // Given

        // When
        urlList.add("https:/www.google.co.kr/");
        urlList.add("https://");
        urlList.add("https://.com");
        urlList.add("https//naver.com");
        urlList.add("http://naver");

        // Then
        for (String url : urlList) {
            boolean b = urlValidation.connectValid(url);
            assertThat(b, is(false));
        }
    }



}