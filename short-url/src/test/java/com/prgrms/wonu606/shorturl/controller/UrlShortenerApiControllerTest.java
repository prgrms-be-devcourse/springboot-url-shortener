package com.prgrms.wonu606.shorturl.controller;

import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateRequest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    class GenerateShortenedUrlTest {

        @ParameterizedTest
        @MethodSource("provideValidUrls")
        void whenUrlIsValid_thenShouldReturnShortenedUrl(ShortenUrlCreateRequest request) throws Exception {
            // Given
            String requestBody = objectMapper.writeValueAsString(request);

            // When & Then
            mockMvc.perform(post("/api/shorten-url")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.shortenUrl").value(matchesPattern("^http(s*):\\/\\/.+\\/\\w{1,8}$")));
        }

        @ParameterizedTest
        @MethodSource("provideInvalidUrls")
        void whenUrlIsInvalid_thenShouldReturnBadRequest(ShortenUrlCreateRequest request) throws Exception {
            // Given
            String requestBody = objectMapper.writeValueAsString(request);

            // When & Then
            mockMvc.perform(post("/api/shorten-url")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(requestBody))
                    .andExpect(status().isBadRequest());
        }


        static Stream<Arguments> provideValidUrls() {
            return Stream.of(
                    Arguments.arguments(new ShortenUrlCreateRequest("https://url.kr")),
                    Arguments.arguments(new ShortenUrlCreateRequest("https://www.stackoverflow.com")),
                    Arguments.arguments(new ShortenUrlCreateRequest("https://comic.naver.com/index")),
                    Arguments.arguments(new ShortenUrlCreateRequest("https://www.youtube.com/watch?v=Yc56NpYW1DM")),
                    Arguments.arguments(new ShortenUrlCreateRequest("https://www.youtube.com/@devbadak"))
            );
        }

        static Stream<Arguments> provideInvalidUrls() {
            return Stream.of(
                    Arguments.arguments(new ShortenUrlCreateRequest(null))
            );
        }
    }
}
