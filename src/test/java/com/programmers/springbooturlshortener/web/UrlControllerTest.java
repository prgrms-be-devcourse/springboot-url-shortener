package com.programmers.springbooturlshortener.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbooturlshortener.domain.url.UrlService;
import com.programmers.springbooturlshortener.domain.url.UrlServiceFacade;
import com.programmers.springbooturlshortener.domain.url.dto.UrlResponseDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;

@WebMvcTest
class UrlControllerTest {

    @InjectMocks
    UrlController urlController;

    @MockBean
    UrlService urlService;

    @MockBean
    UrlServiceFacade urlServiceFacade;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("기본 홈 화면 이동 성공")
    void goHomePageSuccess() throws Exception {

        // when
        ResultActions resultActions = mockMvc.perform(get("/"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @DisplayName("사용자 요청 URL을 Short URL로 변환 성공 후 상세 페이지로 리다이렉트 성공")
    void originUrlToShortUrlAndRedirectSuccess() throws Exception {
        // given
        String originUrl = "https://google.com";
        String algorithm = "Base62";
        String shortUrl = "AAAAAAB";
        long requestCount = 1L;
        UrlResponseDto urlResponseDto = new UrlResponseDto(originUrl, shortUrl, requestCount);

        when(urlServiceFacade.createShortUrl(any(UrlServiceRequestDto.class)))
            .thenReturn(urlResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/shortener")
            .param("originUrl", originUrl)
            .param("algorithm", algorithm)
        );

        // then
        resultActions.andExpect(status().isFound())
            .andExpect(flash().attribute("url", urlResponseDto))
                .andExpect(redirectedUrlPattern("/shortener*"));
    }

    @Test
    @DisplayName("사용자 요청 URL 이 바인딩에 실패하여 index 페이지로 이동")
    void originUrlToShortUrlBindingFailAndGoIndexPage() throws Exception {
        // given
        String originUrl = "not valid url";
        String algorithm = "Base62";

        // when
        ResultActions resultActions = mockMvc.perform(post("/shortener")
                .param("originUrl", originUrl)
                .param("algorithm", algorithm)
        );

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().hasErrors());
    }

    @Test
    @DisplayName("Short URL 상세페이지로 이동 성공")
    void goDetailPageSuccess() throws Exception {
        // given
        String originUrl = "https://google.com";
        String shortUrl = "AAAAAAB";
        Long requestCount = 1L;
        UrlResponseDto urlResponseDto = new UrlResponseDto(originUrl, shortUrl, requestCount);

        // when
        ResultActions resultActions = mockMvc.perform(get("/shortener")
            .flashAttr("url", urlResponseDto)
        );

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(model().attribute("url", urlResponseDto))
            .andExpect(view().name("detail"));
    }

    @Test
    @DisplayName("Short URL 을 받았을 때 원본 URL 로 이동 성공")
    void receiveShortURLAndGoOriginURLSuccess() throws Exception {
        // given
        String shortUrl = "AAAAAAB";
        String originUrl = "google.com";
        UrlResponseDto urlResponseDto = new UrlResponseDto(originUrl, shortUrl, 1L);

        when(urlService.getOriginUrl(shortUrl))
            .thenReturn(urlResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(get("/{shortUrl}", shortUrl));

        // then
        resultActions
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("https://*"));

    }

}