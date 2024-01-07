package kdt.shorturl.url.service;

import kdt.shorturl.url.converter.Base62Converter;
import kdt.shorturl.url.converter.ShortUrlConverter;
import kdt.shorturl.url.domain.Algorithm;
import kdt.shorturl.url.domain.Url;
import kdt.shorturl.url.dto.CreateShortUrlRequest;
import kdt.shorturl.url.dto.CreateShortenUrlResponse;
import kdt.shorturl.url.repository.UrlRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    private ShortUrlConverter shortUrlConverter;

    @AfterEach
    void tearDown() {
        urlRepository.deleteAll();
    }

    @Test
    void 단축URL_생성_성공_테스트() {
        // given
        CreateShortUrlRequest request = new CreateShortUrlRequest("naver.com", Algorithm.BASE62);
        shortUrlConverter = new Base62Converter();

        // when
        CreateShortenUrlResponse response = urlService.findOrGenerateShortenUrl(request);
        String shorten = shortUrlConverter.encoding(response.id().intValue());

        // then
        assertThat(response.isNew()).isTrue();
        assertThat(response.shortenUrl()).isEqualTo(shorten);
    }

    @Test
    void 기존_단축URL_존재할시_가져오기_테스트() {
        // given
        CreateShortUrlRequest request1 = new CreateShortUrlRequest("naver.com", Algorithm.BASE62);
        CreateShortUrlRequest request2 = new CreateShortUrlRequest("naver.com", Algorithm.BASE62);
        shortUrlConverter = new Base62Converter();
        CreateShortenUrlResponse existingUrl = urlService.findOrGenerateShortenUrl(request1);

        // when
        CreateShortenUrlResponse newUrl = urlService.findOrGenerateShortenUrl(request2);

        // then
        assertThat(newUrl.isNew()).isFalse();
        assertThat(existingUrl.id()).isEqualTo(newUrl.id());
    }

    @Test
    void 단축URL로_원본URL_찾기_테스트() {
        // given
        String testUrl = "naver.com";
        CreateShortUrlRequest request = new CreateShortUrlRequest(testUrl, Algorithm.BASE62);
        CreateShortenUrlResponse response = urlService.findOrGenerateShortenUrl(request);

        // when
        String originUrl = urlService.findOriginUrlByShortUrl(response.shortenUrl());

        // then
        assertThat(originUrl).isEqualTo(testUrl);
    }
}
