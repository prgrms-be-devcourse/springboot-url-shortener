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
import org.springframework.test.web.servlet.RequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerApiControllerTest {

    private static final String API_URL = "/api/shorten-url";
    private static final String SHORTEN_URL_PATTERN = "^http(s*):\\/\\/.+\\/\\w{1,8}$";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    class GenerateShortenedUrlMethodTests {

        @ParameterizedTest
        @MethodSource("provideValidUrls")
        void whenUrlIsValid_thenShouldReturnShortenedUrl(ShortenUrlCreateRequest request) throws Exception {
            // When & Then
            mockMvc.perform(performPostRequest(request))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.shortenUrl").value(matchesPattern(SHORTEN_URL_PATTERN)));
        }

        @ParameterizedTest
        @MethodSource("provideInvalidUrls")
        void whenUrlIsInvalid_thenShouldReturnBadRequest(ShortenUrlCreateRequest request) throws Exception {
            // When & Then
            mockMvc.perform(performPostRequest(request))
                    .andExpect(status().isBadRequest());
        }

        private RequestBuilder performPostRequest(ShortenUrlCreateRequest request) throws Exception {
            String requestBody = objectMapper.writeValueAsString(request);

            return post(API_URL)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody);
        }


        static Stream<Arguments> provideValidUrls() {
            return Stream.of(
                    Arguments.of(new ShortenUrlCreateRequest("https://url.kr")),
                    Arguments.of(new ShortenUrlCreateRequest("https://www.stackoverflow.com")),
                    Arguments.of(new ShortenUrlCreateRequest("https://comic.naver.com/index")),
                    Arguments.of(new ShortenUrlCreateRequest("https://www.youtube.com/watch?v=Yc56NpYW1DM")),
                    Arguments.of(new ShortenUrlCreateRequest("https://www.youtube.com/@devbadak"))
            );
        }

        static Stream<Arguments> provideInvalidUrls() {
            String overLengthUrl = "https://".concat("a".repeat(2000));
            return Stream.of(
                    Arguments.of(new ShortenUrlCreateRequest(null)),
                    Arguments.of(new ShortenUrlCreateRequest(overLengthUrl))
            );
        }
    }
}
