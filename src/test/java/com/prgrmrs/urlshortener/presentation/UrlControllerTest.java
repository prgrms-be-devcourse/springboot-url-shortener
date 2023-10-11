package com.prgrmrs.urlshortener.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrmrs.urlshortener.business.UrlService;
import com.prgrmrs.urlshortener.model.UrlMapping;
import com.prgrmrs.urlshortener.model.vo.OriginalUrl;
import com.prgrmrs.urlshortener.presentation.dto.ShortenUrlResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class UrlControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UrlService service;

    ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "/v1/url";
    private static final String TEST_URL = "https://www.google.com";


    @Test
    void shortenUrl() throws Exception {
        // given
        OriginalUrl originalUrl = new OriginalUrl(TEST_URL);

        // when & then
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(originalUrl)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalUrl").exists())
                .andExpect(jsonPath("$.hash").exists());
    }

    @Test
    void redirectUrl() throws Exception {
        // given
        OriginalUrl originalUrl = new OriginalUrl(TEST_URL);
        UrlMapping mapping = service.shortenUrl(originalUrl);
        String hash = mapping.getHash();

        // when & then
        mockMvc.perform(get(BASE_URL + "/" + hash))
                .andDo(print())
                .andExpect(status().isMovedPermanently());
    }

}



