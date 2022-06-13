package com.spring.shorturl.domain;

import com.spring.shorturl.domain.data.MyPageUrl;
import com.spring.shorturl.domain.data.Url;
import com.spring.shorturl.domain.data.UrlDto;
import com.spring.shorturl.domain.function.Base62Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UrlServiceTest {
    @Autowired
    UrlRepository urlRepository;

    @Autowired
    UrlService urlService;

    @Autowired
    MyPageUrl myPageUrl;

    @Test
    @DisplayName("URL 엔티티 저장")
    void saveURL() {
        // given
        UrlDto.SaveRequest dto1 = new UrlDto.SaveRequest("https://google.com");
        UrlDto.SaveRequest dto2 = new UrlDto.SaveRequest("https://naver.com");
        UrlDto.SaveRequest dto3 = new UrlDto.SaveRequest("https://google.com");

        // when
        urlService.save(dto1);
        urlService.save(dto2);
        urlService.save(dto3);

        // then
        Url url1 = urlService.findById(1L);
        Url url2 = urlService.findById(2L);
//        Url url1 = urlRepository.findById(1L).get();
//        Url url2 = urlRepository.findById(2L).get();

        assertThat(url1.getOriginUrl()).isEqualTo("https://google.com");
        assertThat(url2.getOriginUrl()).isEqualTo("https://naver.com");
        assertThat(urlRepository.count()).isEqualTo(2);
    }

    @Test
    @DisplayName("Short URL 값으로 User Entity 를 찾아온다.")
    void findByIdTest() {

        // given
        UrlDto.SaveRequest dto1 = new UrlDto.SaveRequest("https://google.com");
        Long save = urlService.save(dto1);
        String shortUrl = Base62Util.encoding(1);

        // when
        Url url = urlRepository.findByOriginUrl("https://google.com").get();
//        Url url = urlRepository.findByOriginUrl("https://google.com").get();

        // then
        String myPage = myPageUrl.shortUrlWithMyPageName(shortUrl);
        assertThat(url.getShortUrl()).isEqualTo(myPage);
    }

    @Test
    @DisplayName("Short URL 을 통해 original Url 을 요청하면 request count 가 1 증가한다.")
    void checkCount () {

        // given
        UrlDto.SaveRequest dto1 = new UrlDto.SaveRequest("https://google.com");
        Long save = urlService.save(dto1);
        Url url = urlService.findById(save);
        String shortUrl = url.getShortUrl();

        // when
        urlService.findByShortUrl(shortUrl);
        urlService.findByShortUrl(shortUrl);
        urlService.findByShortUrl(shortUrl);
        urlService.findByShortUrl(shortUrl);
        urlService.findByShortUrl(shortUrl);

        url = urlService.findById(save);

        // then
        assertThat(url.getRequestCount()).isEqualTo(5);
    }
}