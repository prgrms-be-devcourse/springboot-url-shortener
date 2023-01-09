package com.example.urlshortenert2.repository;

import com.example.urlshortenert2.model.ShortedUrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ShortedUrlRepositoryTest {

    @Autowired
    private ShortedUrlRepository repository;

    private final String shorteningKey = "1";
    private final String originUrl = "https://www.naver.com";

    @BeforeEach
    void setUp() {
        ShortedUrl shortedUrl = new ShortedUrl(shorteningKey, originUrl);
        repository.save(shortedUrl);
    }

    @Test
    @DisplayName("shorteningKey로 originUrl을 찾을 수 있다")
    void findUrlByKey() {
        Optional<ShortedUrl> shortedUrl = repository.findShortedUrlByShorteningKey(shorteningKey);
        assertThat(shortedUrl.isEmpty()).isEqualTo(false);
        assertThat(shortedUrl.get().getOriginUrl()).isEqualTo(originUrl);
    }

    @Test
    @DisplayName("orginUrl를 이용하여 Shortening Url 정보를 찾을 수 있다.")
    void findShorteningKeyByOringUrl() {
        Optional<ShortedUrl> originShortedUrl = repository.findShortedUrlByOriginUrl(originUrl);
        assertThat(originShortedUrl.isEmpty()).isEqualTo(false);
        assertThat(originShortedUrl.get().getShorteningKey()).isEqualTo(shorteningKey);
    }

    @Test
    @DisplayName("unique로 설정한 컬럼에 null이 중복으로 들어갈 수 있다.")
    void unique_column_null_duplicate_test() {
        ShortedUrl naverUrl = new ShortedUrl("https://www.naver.com");
        ShortedUrl googleUrl = new ShortedUrl("https://www.google.com");

        ShortedUrl savedNaverUrl = repository.save(naverUrl);
        ShortedUrl savedGoogleUrl = repository.save(googleUrl);

        assertThat(savedNaverUrl.getShorteningKey()).isEqualTo(savedGoogleUrl.getShorteningKey());
    }
}