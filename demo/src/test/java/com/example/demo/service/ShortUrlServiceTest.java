package com.example.demo.service;

import com.example.demo.domain.Url;
import com.example.demo.repository.ShortUrlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceTest {

    @InjectMocks
    private ShortUrlService shortUrlService;

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @Test
    @DisplayName("처음 들어오는 url에 대해 short url을 만든다")
    public void createShortUrlTest() {
        //given
        String newUrl = "https://miro.com/?utm_source=google&utm_medium=cpc&utm_" +
                "campaign=S|GOO|BRN|WW|KO-KO|Brand|Exact&utm_adgroup=&utm_custom=10501506958&" +
                "utm_content=447030169338&utm_term=miro&device=c&location=1009871&gclid=" +
                "CjwKCAjwnZaVBhA6EiwAVVyv9NZnYeotOUPYl5Dtnr-YiliYKv1dRIYA3C_FKhlnuUsbwzB8Lcx0nxoCOO8QAvD_BwE";

        Url url = new Url(newUrl, 1);
        ReflectionTestUtils.setField(url, "id", 1000000);
        //mocking
        given(shortUrlRepository.save(any()))
                .willReturn(url);
        given(shortUrlRepository.findUrlByOriginUrl(newUrl))
                .willReturn(Optional.empty());

        //when
        String shortUrl = shortUrlService.createShortUrl(newUrl);

        //then
        assertThat(shortUrl.length()).isLessThan(newUrl.length());
        assertThat(shortUrl.length()).isLessThanOrEqualTo(8);
    }

    @Test
    @DisplayName("이전에 요청온적이 있는 url은 이전과 같은 short url을 리턴한다")
    public void returnSameShortUrl() {
        //given
        String newUrl = "https://miro.com/?utm_source=google&utm_medium=cpc&utm_" +
                "campaign=S|GOO|BRN|WW|KO-KO|Brand|Exact&utm_adgroup=&utm_custom=10501506958&" +
                "utm_content=447030169338&utm_term=miro&device=c&location=1009871&gclid=" +
                "CjwKCAjwnZaVBhA6EiwAVVyv9NZnYeotOUPYl5Dtnr-YiliYKv1dRIYA3C_FKhlnuUsbwzB8Lcx0nxoCOO8QAvD_BwE";

        Url url = new Url(newUrl, 1);
        ReflectionTestUtils.setField(url, "id", 1000000);
        //mocking
        given(shortUrlRepository.save(any()))
                .willReturn(url);
        given(shortUrlRepository.findUrlByOriginUrl(newUrl))
                .willReturn(Optional.empty());

        //when
        String shortUrlFirst = shortUrlService.createShortUrl(newUrl);
        String shortUrlAgain = shortUrlService.createShortUrl(newUrl);

        //then
        assertThat(shortUrlFirst).isEqualTo(shortUrlAgain);
    }
}