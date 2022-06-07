package prgms.marco.springbooturlshortener.service;

import static prgms.marco.springbooturlshortener.exception.Message.DUPLICATE_ORIGIN_URL_EXP_MSG;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import prgms.marco.springbooturlshortener.entity.Url;
import prgms.marco.springbooturlshortener.exception.DuplicateOriginUrlException;
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
        Assertions.assertThat(shortUrl)
            .isEqualTo(urlEncoder.urlEncoder(String.valueOf(savedUrl.getId())));
    }

    @Test
    @DisplayName("ShortUrl 생성 실패 - Url 중복")
    void testCreateShortUrlFail() {
        // given
        String alreadyExistUrl = "www.google.com";
        urlShortenService.createShortUrl(alreadyExistUrl);

        // when
        // then
        Assertions.assertThatThrownBy(
                () -> urlShortenService.createShortUrl(alreadyExistUrl)
            ).isInstanceOf(DuplicateOriginUrlException.class)
            .hasMessageContaining(DUPLICATE_ORIGIN_URL_EXP_MSG.getMessage());
    }

}