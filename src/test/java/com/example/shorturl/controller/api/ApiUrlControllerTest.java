package com.example.shorturl.controller.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.shorturl.domain.Algorithm;
import com.example.shorturl.dto.request.ShortUrlCreateRequest;
import com.example.shorturl.dto.response.OriginUrlResponse;
import com.example.shorturl.dto.response.ShortUrlResponse;
import com.example.shorturl.service.UrlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class ApiUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UrlService urlService;

    @Test
    void shortUrl_생성_호출_테스트() throws Exception {
        // Given
        ShortUrlCreateRequest request = new ShortUrlCreateRequest("https://www.example.com", Algorithm.BASE_62);
        ShortUrlResponse response = new ShortUrlResponse("http://localhost:8080/test", 0);
        given(urlService.createOrGetShortUrl(any())).willReturn(response);


        // When
        mockMvc.perform(post("/url")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.shortUrl").value(response.shortUrl()))
            .andExpect(jsonPath("$.requestCount").value(response.requestCount()));

        // Then
        verify(urlService).createOrGetShortUrl(eq(request));
    }

    @Test
    void originUrl로_리다이렉트_호출_테스트() throws Exception {
        // Given
        String shortUrl = "/abc123";
        String originalUrl = "https://www.example.com";
        given(urlService.getOriginUrl(any())).willReturn(new OriginUrlResponse(originalUrl));

        // When & Then
        mockMvc.perform(get(shortUrl))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(originalUrl));
    }
}
