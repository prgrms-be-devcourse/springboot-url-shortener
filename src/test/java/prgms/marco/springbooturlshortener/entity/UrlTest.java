package prgms.marco.springbooturlshortener.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import prgms.marco.springbooturlshortener.repository.UrlRepository;

@DataJpaTest
class UrlTest {

    @Autowired
    UrlRepository urlRepository;

    @Test
    @DisplayName("생성 테스트")
    void testCreateSuccess() {
        //given when
        Url url = Url.createUrl("www.originUrl.com");

        //then
        assertAll(
            () -> assertThat(url).isNotNull(),
            () -> assertThat(url.getOriginUrl()).isEqualTo("www.originUrl.com"),
            () -> assertThat(url.getShortUrl()).isNull(),
            () -> assertThat(url.getReqCount()).isZero()
        );
    }

    @Test
    @DisplayName("")
    void testConvertIdToShortUrl() {
        Url url = Url.createUrl("www.originUrl.com");
        Url savedUrl = urlRepository.save(url);

        savedUrl.convertIdToShortUrl(savedUrl.getId());

        UrlEncoder urlEncoder = new UrlEncoder();
        assertThat(savedUrl.getShortUrl()).isEqualTo(urlEncoder.encoding(savedUrl.getId()));
    }
}