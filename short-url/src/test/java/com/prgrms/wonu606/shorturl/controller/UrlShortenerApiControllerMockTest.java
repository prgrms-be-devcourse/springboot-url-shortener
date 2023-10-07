package com.prgrms.wonu606.shorturl.controller;

import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateRequest;
import com.prgrms.wonu606.shorturl.controller.mapper.UrlShortenerApiParamMapperImpl;
import com.prgrms.wonu606.shorturl.controller.mapper.UrlShortenerApiResponseMapperImpl;
import com.prgrms.wonu606.shorturl.service.UrlShortenerService;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateResult;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@WebMvcTest(UrlShortenerApiController.class)
@Import({UrlShortenerApiParamMapperImpl.class, UrlShortenerApiResponseMapperImpl.class})
@TestPropertySource(locations = "/application.properties")
public class UrlShortenerApiControllerMockTest {

    private static final String API_URL = "/api/shorten-url";
    private static final String SHORTEN_URL_PATTERN = "^http(s*):\\/\\/.+\\/\\w{1,8}$";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UrlShortenerService urlShortenerService;

    @Nested
    class GenerateShortenedUrlTest {

        @ParameterizedTest
        @MethodSource("provideValidUrls")
        void whenUrlIsValid_thenShouldReturnShortenedUrl(ShortenUrlCreateRequest request, ShortenUrlCreateResult result)
                throws Exception {
            // Given
            given(urlShortenerService.createShortenUrlHash(any()))
                    .willReturn(result);

            // When & Then
            mockMvc.perform(performPostRequest(request))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.shortenUrl").value(matchesPattern(SHORTEN_URL_PATTERN)));
        }

        private RequestBuilder performPostRequest(ShortenUrlCreateRequest request) throws Exception {
            String requestBody = objectMapper.writeValueAsString(request);

            return post(API_URL)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody);
        }

        static Stream<Arguments> provideValidUrls() {
            return Stream.of(
                    Arguments.of(
                            new ShortenUrlCreateRequest("https://url.kr"),
                            new ShortenUrlCreateResult("12345678")
                    ),
                    Arguments.of(
                            new ShortenUrlCreateRequest("https://www.stackoverflow.com"),
                            new ShortenUrlCreateResult("abcdefg")
                    ),
                    Arguments.of(
                            new ShortenUrlCreateRequest("https://comic.naver.com/index"),
                            new ShortenUrlCreateResult("ABCDFE")
                    ),
                    Arguments.of(
                            new ShortenUrlCreateRequest("https://www.youtube.com/watch?v=Yc56NpYW1DM"),
                            new ShortenUrlCreateResult("abc123AB")
                    ),
                    Arguments.of(
                            new ShortenUrlCreateRequest("https://www.youtube.com/@devbadak"),
                            new ShortenUrlCreateResult("A")
                    )
            );
        }
    }
}
