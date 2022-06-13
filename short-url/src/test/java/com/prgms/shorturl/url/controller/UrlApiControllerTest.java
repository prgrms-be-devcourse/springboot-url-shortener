package com.prgms.shorturl.url.controller;

import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgms.shorturl.url.domain.Url;
import com.prgms.shorturl.url.dto.UrlRequestDto;
import com.prgms.shorturl.url.repository.UrlRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class UrlApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UrlRepository urlRepository;

    @Test
    @DisplayName("원본 Url 주소가 입력되었을 때 원본 Url과 shortUrl을 반환한다.")
    void shortenUrlTest() throws Exception {
        String longUrl = "https://programmers.co.kr/";
        UrlRequestDto urlRequestDto = new UrlRequestDto(longUrl);
        given(urlRepository.save(new Url(longUrl)))
            .willReturn(new Url(100L, longUrl));

        mockMvc.perform(MockMvcRequestBuilders.post("/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(urlRequestDto)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("shortUrl이 저장되어 있는 경우 원본 Url로 redirect 된다.")
    void redirectUrlTest() throws Exception {
        given(urlRepository.findById(100L))
            .willReturn(Optional.of(new Url(100L, "https://programmers.co.kr/")));

        mockMvc.perform(MockMvcRequestBuilders.get("/{shortUrl}", "mB")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andDo(MockMvcResultHandlers.print());
    }
}