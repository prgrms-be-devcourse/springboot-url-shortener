package com.prgrms.urlshortener.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.prgrms.urlshortener.domain.ShortedUrl;
import com.prgrms.urlshortener.domain.Url;

@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @DisplayName("단축 Url로 Url를 찾는다.")
    @Test
    void findUrlByShortedUrl() {
        // given
        Url url = new Url("https://studyhardd.tistory.com/7612312311");
        ShortedUrl shortedUrl = new ShortedUrl("1234");
        url.assignShortedUrl(shortedUrl);
        urlRepository.save(url);

        // when
        Optional<Url> findUrl = urlRepository.findUrlByShortedUrl(shortedUrl);

        // then
        assertThat(findUrl).isNotEmpty()
            .get()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(url);
    }

}
