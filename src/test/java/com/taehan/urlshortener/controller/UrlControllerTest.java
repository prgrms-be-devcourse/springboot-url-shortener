package com.taehan.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.taehan.urlshortener.dto.UrlRequestDto;
import com.taehan.urlshortener.model.AlgorithmType;
import com.taehan.urlshortener.model.Url;
import com.taehan.urlshortener.service.UrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UrlController.class)
@AutoConfigureMockMvc
class UrlControllerTest {
    @MockBean
    private UrlService urlService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("short Url 변환 성공 테스트")
    void testConvert() throws Exception {
        UrlRequestDto urlRequestDto = new UrlRequestDto("www.naver.com", AlgorithmType.BASE62);
        Optional<Url> url = Optional.of(new Url("www.naver.com", "B", AlgorithmType.BASE62));

        given(urlService.save(urlRequestDto)).willReturn(1L);
        given(urlService.findById(1L)).willReturn(url);
        mockMvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(urlRequestDto))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortUrl").value(url.get().getShortUrl()))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("short Url 변환 성공 테스트")
    void testGetShortUrl() throws Exception {
        String shortUrl = "B";

        given(urlService.getOriginalUrl(shortUrl)).willReturn(Optional.of("naver.com"));
        mockMvc.perform(get("/{shortUrl}", shortUrl)
        )
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "naver.com"))
                .andDo(print())
        ;
    }


}