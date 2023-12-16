package com.springboot.springbooturlshortner.controller;

import com.springboot.springbooturlshortner.service.ShortenUrlRequestDto;
import com.springboot.springbooturlshortner.service.UrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UrlService urlService;
    @Value("${spring.start-url}")
    private String startUrl;

    @Test
    @DisplayName("url 단축 요청 성공")
    void Success_ShrotenUrl() throws Exception {
        // given
        String originUrl = "https://test-url";
        String shortenUrl = startUrl + "/" + "testkey";
        when(urlService.createShortenUrl(any(ShortenUrlRequestDto.class))).thenReturn(shortenUrl);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/shorten-url")
                        .param("originUrl", originUrl) // URL 인코딩이 필요한 경우에는 여기에서 수행
                        .contentType("application/x-www-form-urlencoded"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("shortenUrl"))
                .andExpect(model().attribute("shortenUrl", shortenUrl));
    }

    @Test
    @DisplayName("기존 url로 리다이렉트 성공")
    void Success_RedirectToOriginUrl() throws Exception {
        // given
        String originUrl = "https://test-url";
        String uniqueKey = "testkey";
        when(urlService.getOriginUrl(uniqueKey)).thenReturn(originUrl);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/jeurl.com/{uniqueKey}", uniqueKey))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(originUrl));
    }
}