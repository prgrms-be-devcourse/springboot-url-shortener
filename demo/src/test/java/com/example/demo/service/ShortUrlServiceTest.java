package com.example.demo.service;

import com.example.demo.domain.Url;
import com.example.demo.exception.ShortUrlNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

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

    @Test
    @DisplayName("shortURL로 원본URL을 가져온다")
    void getOriginUrlTest() {
        //given
        String originUrl = "https://miro.com/?utm_source=google&utm_medium=cpc&utm_" +
                "campaign=S|GOO|BRN|WW|KO-KO|Brand|Exact&utm_adgroup=&utm_custom=10501506958&" +
                "utm_content=447030169338&utm_term=miro&device=c&location=1009871&gclid=" +
                "CjwKCAjwnZaVBhA6EiwAVVyv9NZnYeotOUPYl5Dtnr-YiliYKv1dRIYA3C_FKhlnuUsbwzB8Lcx0nxoCOO8QAvD_BwE";

        String shortUrl = "Ab3df";

        Url url = new Url(originUrl, 1);

        given(shortUrlRepository.findById(any()))
                .willReturn(Optional.of(url));
        //when
        String returnUrl = shortUrlService.getOriginUrl(shortUrl);

        //then
        assertThat(returnUrl).isEqualTo(originUrl);
    }

    @Test
    @DisplayName("저장되지 않은 URL을 가져오려고하면 에러가 발생한다")
    void getInvalidUrlTest() {

        //given
        String shortUrl = "Ab3df";

        given(shortUrlRepository.findById(any()))
                .willReturn(Optional.empty());
        //when

        //then
        assertThrows(ShortUrlNotFoundException.class, () -> shortUrlService.getOriginUrl(shortUrl));
    }

    @Test
    @DisplayName("기존에 있던 URL을 단축 요청하면 호출 횟수가 증가한다")
    void increaseShortUrlCalledTimes() {

        //given
        String newUrl = "https://miro.com/?utm_source=google&utm_medium=cpc&utm_" +
                "campaign=S|GOO|BRN|WW|KO-KO|Brand|Exact&utm_adgroup=&utm_custom=10501506958&" +
                "utm_content=447030169338&utm_term=miro&device=c&location=1009871&gclid=" +
                "CjwKCAjwnZaVBhA6EiwAVVyv9NZnYeotOUPYl5Dtnr-YiliYKv1dRIYA3C_FKhlnuUsbwzB8Lcx0nxoCOO8QAvD_BwE";

        Url url = new Url(newUrl, 1);
        ReflectionTestUtils.setField(url, "id", 1000000);

        //mocking
        given(shortUrlRepository.findUrlByOriginUrl(newUrl))
                .willReturn(Optional.of(url));
        //when
        String againShortenUrl = shortUrlService.createShortUrl(newUrl);

        //then
        assertThat(url.getCalledTimes()).isEqualTo(2);
    }
}