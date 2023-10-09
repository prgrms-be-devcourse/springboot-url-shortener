package com.prgrms.wonu606.shorturl.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateRequest;
import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ShortUrlApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenShortUrlExists_thenRedirect() throws Exception {
        // Given
        String originalUrl = "https://naver.com";
        ShortenUrlCreateRequest request = new ShortenUrlCreateRequest(originalUrl);

        // When & Then
        String responseJson = mockMvc.perform(post("/api/shorten-url")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString();

        ShortenUrlCreateResponse response = objectMapper.readValue(responseJson, ShortenUrlCreateResponse.class);
        String shortUrl = response.shortenUrl().substring(response.shortenUrl().lastIndexOf("/"));

        mockMvc.perform(get(shortUrl))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", originalUrl));
    }
}
