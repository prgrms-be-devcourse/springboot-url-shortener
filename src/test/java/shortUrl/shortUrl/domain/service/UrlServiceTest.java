package shortUrl.shortUrl.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shortUrl.shortUrl.domain.dto.CreateShortUrlDto;
import shortUrl.shortUrl.domain.value.Algorithm;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UrlServiceTest {

    @Autowired
    UrlService urlService;

    @BeforeEach
    void setup() {
        String originalUrl = "naver.com";
        base56UrlDto = new CreateShortUrlDto(originalUrl, Algorithm.BASE_56);
        sha256UrlDto = new CreateShortUrlDto(originalUrl, Algorithm.SHA_256);
    }

    CreateShortUrlDto base56UrlDto;
    CreateShortUrlDto sha256UrlDto;

    @Nested
    class 생성_테스트 {

        @Test
        void shortUrl_생성_테스트() {
            String shortUrl = urlService.createShortUrl(base56UrlDto);
        }
    }




}