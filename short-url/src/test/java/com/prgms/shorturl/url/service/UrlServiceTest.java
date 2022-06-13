package com.prgms.shorturl.url.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.prgms.shorturl.url.domain.Url;
import com.prgms.shorturl.url.repository.UrlRepository;
import com.prgms.shorturl.util.BaseUtil;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@Slf4j
@SpringBootTest
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private BaseUtil baseUtil;

    @MockBean(name = "urlRepository")
    private UrlRepository urlRepository;

    @Test
    @DisplayName("새로운 Url이 주어졌을 때 db에 저장후 id 값을 이용해서 encoding 후 shortUrl을 반환한다.")
    void shortenUrlTest() {
        // Given
        String longUrl = "https://programmers.co.kr/";

        given(urlRepository.save(new Url(longUrl)))
            .willReturn(new Url(100L, longUrl));

        // When
        String shortUrl = urlService.shortenUrl(longUrl);

        // Then
        assertThat(shortUrl).isEqualTo("http://localhost:8080/" + baseUtil.encoding(100L));
    }

    @Test
    @DisplayName("shortUrl이 주어졌을 때 longUrl 반환한다. 또한 shortUrl 에 대한 요청 할때마다 callCount가 1씩 오른다.")
    void getLongUrlByShortUrlTest() {
        // Given
        String shortUrl = baseUtil.encoding(100L);
        given(urlRepository.findById(100L))
            .willReturn(Optional.of(new Url(100L, "https://programmers.co.kr/")));

        // When
        String longUrl = urlService.getLongUrlByShortUrl(shortUrl);
        long callCount = urlService.getCallCount(shortUrl);

        // Then
        assertThat(longUrl).isEqualTo("https://programmers.co.kr/");
        assertThat(callCount).isEqualTo(1L);
    }

}