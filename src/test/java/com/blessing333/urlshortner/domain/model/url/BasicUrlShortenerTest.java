package com.blessing333.urlshortner.domain.model.url;

import com.blessing333.urlshortner.domain.model.url.key.Base62KeyGenerator;
import com.blessing333.urlshortner.domain.model.url.shortener.impl.BasicUrlShortener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BasicUrlShortenerTest {
    private static final String HOST = "http://localhost:8080/";
    private final Base62KeyGenerator keyGenerator = new Base62KeyGenerator();
    private final BasicUrlShortener urlShortener = new BasicUrlShortener(keyGenerator);

    @DisplayName("기본 url 단축 전략은 url sequence를 base62로 인코딩하여 인코딩된 키 값을 단축 url로 사용한다")
    @Test
    void testBasicUrlShortening() {
        UrlCreateCommand urlCreateCommand = new BasicUrlCreateCommand("https://naver.com");
        urlCreateCommand.setUrlSequence(192875981297L);
        String encodedKey = keyGenerator.generateKey(urlCreateCommand.getUrlSequence());

        String shortUrl = urlShortener.shorteningUrl(urlCreateCommand).getNewUrl();

        assertThat(shortUrl).isEqualTo(HOST + encodedKey);
    }
}