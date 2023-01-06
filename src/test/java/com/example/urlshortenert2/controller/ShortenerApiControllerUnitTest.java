package com.example.urlshortenert2.controller;

import com.example.urlshortenert2.dto.ShortenerRequestDto;
import com.example.urlshortenert2.dto.ShortenerResponseDto;
import com.example.urlshortenert2.service.ShortenerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
class ShortenerApiControllerUnitTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ShortenerService shortenerService;

    @Test
    @DisplayName("쇼트너 url 생성 테스트")
    void createShortener() throws Exception {
        //given
        ShortenerRequestDto testRequestDto = new ShortenerRequestDto("https://www.naver.com");
        ShortenerResponseDto testResponseDto = new ShortenerResponseDto("1");
        when(shortenerService.createShortener(testRequestDto)).thenReturn(testResponseDto);
        String requestBody = objectMapper.writeValueAsString(testRequestDto);
        //when
        mockMvc
                .perform(post("/api/v1/shortener")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("shorteningKey").value("1"))
                .andDo(print());
        //then
        // TODO: 2023-01-05 몇번 실행 되는지 뽑는 함수가 있지 않았나?
        verify(shortenerService).createShortener(testRequestDto);
    }

    @Test
    @DisplayName("원본 url로 리다이렉트 테스트")
    void getOriginURL() throws Exception {
        //given
        String shortedkey = "1";
        String originUrl = "https://www.naver.com";
        when(shortenerService.findByShorteningKey(shortedkey))
                .thenReturn(originUrl);
        //when
        mockMvc.perform(get("/{shorteningKey}", shortedkey))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", originUrl))
                .andDo(print());
        //then
        verify(shortenerService).findByShorteningKey(shortedkey);
    }

    @Test
    @DisplayName("프로토콜 형식이 없는 URL 요청 시 BAD_REQUEST를 반환한다.")
    void illegalURLTest() throws Exception {
        // given
        String shortedKey = "1";
        String orgingUrl = "www.naver.com";
        ShortenerRequestDto testRequestDto = new ShortenerRequestDto(orgingUrl);
        ShortenerResponseDto testResponseDto = new ShortenerResponseDto(shortedKey);
        String requestBody = objectMapper.writeValueAsString(testRequestDto);
        // when, then
        mockMvc.perform(post("/api/v1/shortener")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("접속할 수 없는 URL 요청 시 BAD_REQUEST를 반환한다.")
    void notConnectionTest() throws Exception {
        // given
        String shortedKey = "1";
        String orgingUrl = "https://www.naver.com/dltndud";
        ShortenerRequestDto testRequestDto = new ShortenerRequestDto(orgingUrl);
        ShortenerResponseDto testResponseDto = new ShortenerResponseDto(shortedKey);
        String requestBody = objectMapper.writeValueAsString(testRequestDto);
        // when, then
        mockMvc.perform(post("/api/v1/shortener")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 도메인을 가진 URL 요청 시 BAD_REQUEST를 반환한다.")
    void notPresentDomainURLTest() throws Exception {
        // given
        String shortedKey = "1";
        String orgingUrl = "https://eisljzeijdk/fdjseis";
        ShortenerRequestDto testRequestDto = new ShortenerRequestDto(orgingUrl);
        ShortenerResponseDto testResponseDto = new ShortenerResponseDto(shortedKey);
        String requestBody = objectMapper.writeValueAsString(testRequestDto);
        // when, then
        mockMvc.perform(post("/api/v1/shortener")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}