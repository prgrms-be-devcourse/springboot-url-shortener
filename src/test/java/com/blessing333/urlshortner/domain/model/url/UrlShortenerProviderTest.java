package com.blessing333.urlshortner.domain.model.url;

import com.blessing333.urlshortner.domain.model.url.shortener.UrlShorteningFailException;
import com.blessing333.urlshortner.domain.model.url.shortener.impl.UrlShortenerProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UrlShortenerProviderTest {
    private final String HOST = "http://localhost:8080/";
    @Autowired
    UrlShortenerProvider provider = new UrlShortenerProvider();


    @DisplayName("기본 url 단축 요청을 처리할 수 있어야 한다.")
    @Test
    void testBasicUrlShortening() {
        BasicUrlCreateCommand command = new BasicUrlCreateCommand("https://naver.com");
        command.setUrlSequence(3521614606207L);

        String shortUrl = provider.shorteningUrl(command).getNewUrl();

        Assertions.assertThat(shortUrl).isEqualTo(HOST + "ZZZZZZZ");
    }

    @DisplayName("커스텀 도메인 url 단축 요청을 처리할 수 있어야 한다.")
    @Test
    void testCustomDomainUrlShortening() {
        CustomDomainUrlCreateCommand command = new CustomDomainUrlCreateCommand("https://naver.com", "domain");
        command.setUrlSequence(3521614606207L);

        String shortUrl = provider.shorteningUrl(command).getNewUrl();

        Assertions.assertThat(shortUrl).isEqualTo(HOST + "domain/" + "ZZZZZZZ");
    }

    @DisplayName("url 단축 과정에서 예외가 발생하면 UrlShorteningFailException을 던진다")
    @Test
    void testShorteningFail() {
        UrlCreateCommand urlCreateCommand = new BasicUrlCreateCommand("https://naver.com");
        Long invalidUrlId = 3521614606208L;
        urlCreateCommand.setUrlSequence(invalidUrlId);

        assertThrows(UrlShorteningFailException.class, () -> provider.shorteningUrl(urlCreateCommand));
    }
}