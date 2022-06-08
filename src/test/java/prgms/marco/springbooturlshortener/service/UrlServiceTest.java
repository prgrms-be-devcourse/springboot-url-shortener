package prgms.marco.springbooturlshortener.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static prgms.marco.springbooturlshortener.exception.Message.DUPLICATE_ORIGIN_URL_EXP_MSG;
import static prgms.marco.springbooturlshortener.exception.Message.INVALID_SHORT_URL_EXP_MSG;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import prgms.marco.springbooturlshortener.entity.Url;
import prgms.marco.springbooturlshortener.exception.DuplicateOriginUrlException;
import prgms.marco.springbooturlshortener.exception.InvalidShortUrlException;
import prgms.marco.springbooturlshortener.repository.UrlRepository;
import prgms.marco.springbooturlshortener.service.utils.UrlEncoder;

@DataJpaTest
class UrlServiceTest {

    @Autowired
    private UrlRepository urlRepository;

    private UrlEncoder urlEncoder = new UrlEncoder();

    private UrlService urlShortenService;

    @BeforeEach
    void setUp() {
        urlShortenService = new UrlService(
            urlRepository,
            urlEncoder
        );
    }

    @Test
    @DisplayName("Shorturl 생성 성공")
    void testCreateShortUrlSuccess() {
        //given
        String originUrl = "www.naver.com";

        //when
        String shortUrl = urlShortenService.createShortUrl(originUrl);

        //then
        Url savedUrl = urlRepository.findByOriginUrl(originUrl).get();
        assertThat(shortUrl)
            .isEqualTo(urlEncoder.urlEncoder(String.valueOf(savedUrl.getId())));
    }

    @Test
    @DisplayName("ShortUrl 생성 실패 - Url 중복")
    void testCreateShortUrlFail() {
        // given
        String alreadyExistUrl = "www.google.com";
        urlShortenService.createShortUrl(alreadyExistUrl);

        // when
        String shortUrl = urlShortenService.createShortUrl(alreadyExistUrl);

        // then
        Url savedUrl = urlRepository.findByOriginUrl(alreadyExistUrl).get();
        assertThat(shortUrl).isEqualTo(urlEncoder.urlEncoder(String.valueOf(savedUrl.getId())));
    }

    @Test
    @DisplayName("ShortUrl로 OriginUrl 조회 - 성공")
    void testFindOriginUrlByShortUrlSuccess() {
        // given
        String originUrl = "www.google.com";
        String shortUrl = urlShortenService.createShortUrl(originUrl);

        // when
        String findUrl = urlShortenService.findOriginUrlByShortUrl(shortUrl);

        // then
        Url url = urlRepository.findByOriginUrl(originUrl).get();
        assertAll(
            () -> assertThat(originUrl).isEqualTo(findUrl),
            () -> assertThat(url.getReqCount()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("ShortUrl로 OriginUrl 조회 - 실패")
    void testFindOriginUrlByShortUrlFail() {
        // given
        // when
        // then
        assertThatThrownBy(
            () -> urlShortenService.findOriginUrlByShortUrl("http://localhost:8080/KDB17")
        ).isInstanceOf(InvalidShortUrlException.class)
            .hasMessageContaining(INVALID_SHORT_URL_EXP_MSG.getMessage());
    }
}