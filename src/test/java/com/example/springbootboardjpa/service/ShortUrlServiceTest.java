package com.example.springbootboardjpa.service;

import com.example.springbootboardjpa.dto.CreateShortUrlDto;
import com.example.springbootboardjpa.dto.ShortUrlDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ShortUrlServiceTest {
    @Autowired
    private ShortUrlService shortUrlService;

    @Test
    void short_id_create_test() {
        //1. given
        CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto()
                .builder()
                .url("https://www.naver.com/?mobile")
                .build();
        //2. when
        ShortUrlDto shortUrlDto = shortUrlService.create(createShortUrlDto);
        //3. then
        assertThat(Pattern.matches("([a-zA-Z0-9]){5}", shortUrlDto.getShortId()), is(true));
        assertThat(shortUrlDto.getUrl(), is("https://www.naver.com/?mobile"));
    }

    @Test
    void short_id_create_with_same_url_twice_test() {
        //1. given
        CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto()
                .builder()
                .url("https://www.naver.com/?mobile")
                .build();
        //2. when
        ShortUrlDto shortUrlDto1 = shortUrlService.create(createShortUrlDto);
        ShortUrlDto shortUrlDto2 = shortUrlService.create(createShortUrlDto);
        //3. then
        assertThat(shortUrlDto1.getShortId(), is(shortUrlDto2.getShortId()));
        assertThat(shortUrlDto1.getUrl(), is(shortUrlDto2.getUrl()));
    }

    @Test
    void short_id_with_wrong_url_test1() {
        //1. given
        CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto()
                .builder()
                .url("https://www.naver")
                .build();
        //2. when + then
        assertThrows(RuntimeException.class, ()->{
            shortUrlService.create(createShortUrlDto);
        });
    }

    @Test
    void short_id_with_wrong_url_test2() {
        //1. given
        CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto()
                .builder()
                .url("wrong url")
                .build();
        //2. when + then
        assertThrows(RuntimeException.class, ()->{
            shortUrlService.create(createShortUrlDto);
        });
    }

    @Test
    void get_short_id_test() {
        //1. given
        CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto()
                .builder()
                .url("https://www.naver.com/?mobile")
                .build();
        ShortUrlDto shortUrlDto1 = shortUrlService.create(createShortUrlDto);
        //2. when
        ShortUrlDto shortUrlDto2 = shortUrlService.read(shortUrlDto1.getShortId());
        //3. then
        assertThat(shortUrlDto1.getShortId(), is(shortUrlDto2.getShortId()));
        assertThat(Pattern.matches("([a-zA-Z0-9]){5}", shortUrlDto2.getShortId()), is(true));
    }
}