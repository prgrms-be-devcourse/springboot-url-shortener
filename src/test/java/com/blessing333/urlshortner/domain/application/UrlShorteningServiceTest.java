package com.blessing333.urlshortner.domain.application;

import com.blessing333.urlshortner.domain.model.url.BasicUrlCreateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlShorteningServiceTest {
    @Autowired
    UrlService service;

    @DisplayName("단축 Url 등록 테스트")
    @Test
    void registerShortenUrl() {
        BasicUrlCreateCommand command = new BasicUrlCreateCommand("https://naver.com");
        command.setUrlSequence(100000L);

        String s = service.registerShortenUrl(command);
        System.out.println(s);

    }
}