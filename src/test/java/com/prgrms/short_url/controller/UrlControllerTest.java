package com.prgrms.short_url.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.short_url.domain.Url;
import com.prgrms.short_url.dto.UrlDto;
import com.prgrms.short_url.exception.NotFoundException;
import com.prgrms.short_url.repository.UrlRepository;
import com.prgrms.short_url.service.UrlService;
import com.prgrms.short_url.validation.UrlValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UrlValidation urlValidation;

    private String url;

    private UrlDto urlDto;

    @BeforeEach
    void setup() {
        url = "www.naver.com";
        urlDto = new UrlDto(url);
    }

    @Test
    @DisplayName("short url 생성 api 테스트")
    void createShortUrlTest() throws Exception {
        // Given
        String originUrl = urlValidation.httpValid(url);

        // When
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(urlDto)))
                .andExpect(status().isOk())
                .andDo(print());

        // Then
        Url url = urlRepository.getUrlByOriginalUrl(originUrl)
                .orElseThrow(NotFoundException::new);

        assertThat(url.getOriginalUrl().equals(originUrl), is(true));
    }
    @Test
    @DisplayName("short url 생성 실패 테스트")
    void createShortUrlFailTest() throws Exception {
        // Given
        url = "http://naver";
        urlDto = new UrlDto(url);
        String originUrl = urlValidation.httpValid(url);

        // When
        assertThrows(Exception.class, () ->
                mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urlDto)))
        );

        // Then
        assertThrows(NotFoundException.class, () -> urlService.getShortUrlByOriginalUrl(originUrl));
    }

    @Test
    @DisplayName("리다이렉트 테스트")
    void redirectTest() throws Exception {
        // Given
        String originUrl = urlValidation.httpValid(url);

        // When
        urlService.createShortUrl(urlDto);
        String shortUrl = urlService.getShortUrlByOriginalUrl(originUrl);
        shortUrl = shortUrl.substring(22);

        // Then
        mockMvc.perform(get("/" + shortUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("리다이렉트 실패 테스트")
    void redirectFailTest() throws Exception {
        // Given
        String originUrl = urlValidation.httpValid(url);

        // When
        urlService.createShortUrl(urlDto);
        String shortUrl = urlService.getShortUrlByOriginalUrl(originUrl);
        String redirectUrl = shortUrl.substring(22) + "T";

        // Then
        assertThrows(Exception.class, () -> mockMvc.perform(get("/" + redirectUrl)
                .contentType(MediaType.APPLICATION_JSON)));
    }


}