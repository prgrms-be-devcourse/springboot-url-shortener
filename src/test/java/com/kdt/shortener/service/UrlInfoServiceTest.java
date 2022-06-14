package com.kdt.shortener.service;

import com.kdt.shortener.repository.UrlInfoRepository;
import com.kdt.shortener.utils.Base62Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
class UrlInfoServiceTest {

    @Autowired
    private UrlInfoRepository repository;

    @Autowired
    private Base62Utils utils;

    private UrlInfoService service;

    @BeforeEach
    void init() {
        service = new UrlInfoService(repository, utils);
    }

    @Test
    @DisplayName("단축 URL 생성 테스트")
    void convertUrlTest(){
        //given
        String originUrl = "http://localhost:8080/test";
        String shortUrl = service.makeShortUrl(originUrl);

        var result = service.turnOriginUrl(shortUrl);

        //then
        assertThat(result.getOriginUrl(), is(originUrl));
    }

    @Test
    @DisplayName("잘못된 shortURL 입력 테스트")
    void illegalShortUrlInputTest(){

        //given
        String wrongUrl = "http://localhost:8080/short/ERROR";

        assertThrows(IllegalArgumentException.class, () ->
                service.turnOriginUrl(wrongUrl));

    }

}