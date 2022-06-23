package com.ryu.urlshortner.url.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryu.urlshortner.url.application.UrlService;
import com.ryu.urlshortner.url.application.dto.request.UrlTransformDto;
import com.ryu.urlshortner.url.application.dto.response.UrlDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(UrlController.class)
public class UrlControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UrlService urlService;

    @Nested
    class Url_축약_요청이_들어올_때 {

        final String url = "/api/v1/urls";

        @Nested
        class originUrl이_존재하지_않거나_빈_값이면 {

            @ParameterizedTest
            @NullAndEmptySource
            void BadRequest를_응답한다(String originUrl) throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("originUrl", originUrl);
                requestMap.put("expiredAt", "2022-06-01T00:00:00");

                //when
                final ResultActions resultActions = transformUrlPerform(url, requestMap);

                //then
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        class originUrl이_유효하지_않은_형식이면 {

            @ParameterizedTest
            @ValueSource(strings = {
                    "http://.com",
                    "http://com.",
                    "http:// ",
                    "http://test",
                    "http://www.test",
                    "ftp://::::@test.com",
                    "test.test.com",
                    "test.com",
                    "www.test",
                    "www.test-.com",
                    "www.-test.com",
                    "www.test#.com"
            })
            void BadRequest를_응답한다(String originUrl) throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("originUrl", originUrl);
                requestMap.put("expiredAt", "2022-12-25T00:00:00");

                //when
                final ResultActions resultActions = transformUrlPerform(url, requestMap);

                //then
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        class expiredAt이_존재하지_않으면 {

            @ParameterizedTest
            @NullSource
            void BadRequest를_응답한다(LocalDateTime expiredAt) throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("originUrl", "https://www.test.com");
                requestMap.put("expiredAt", expiredAt);

                //when
                final ResultActions resultActions = transformUrlPerform(url, requestMap);

                //then
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        class expiredAt이_유효하지_않은_형식이면 {

            @ParameterizedTest
            @ValueSource(strings = {
                    "2022-12-2500:00:00",
                    "2022-12-25",
                    "2022-12",
                    "12-25",
                    "2022",
                    "00:00:00"
            })
            void BadRequest를_응답한다(String expiredAt) throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("originUrl", "https://www.test.com");
                requestMap.put("expiredAt", expiredAt);

                //when
                final ResultActions resultActions = transformUrlPerform(url, requestMap);

                //then
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        class 유효한_originUrl_expiredAt이면 {

            @Test
            void Created를_응답한다() throws Exception {
                //given
                final String originUrl = "https://www.test.com";
                final String expiredAt = "2022-12-25T00:00:00";
                final String shortUrl = "http://localhost:8080/api/v1/urls/TEST123";
                final UrlDto urlDto = UrlDto.builder()
                        .originUrl(originUrl)
                        .shortUrl(shortUrl)
                        .build();
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("originUrl", originUrl);
                requestMap.put("expiredAt", expiredAt);
                doReturn(urlDto).when(urlService).transform(any(UrlTransformDto.class));

                //when
                final ResultActions resultActions = transformUrlPerform(url, requestMap);

                //then
                verify(urlService, times(1)).transform(any(UrlTransformDto.class));
                resultActions.andExpect(status().isCreated())
                        .andExpect(jsonPath("originUrl").value(urlDto.getOriginUrl()))
                        .andExpect(jsonPath("shortUrl").value(urlDto.getShortUrl()));
            }
        }
    }

    @Nested
    class Url_redirect_요청이_들어올_때 {

        final String url = "/api/v1/urls/{shortUrl}";

        @Nested
        class 축약된_Url을_받으면 {

            @Test
            void 원래의_Url로_redirect하고_FOUND를_응답한다() throws Exception {
                //given
                final String shortUrl = "TEST";
                final String originUrl = "https://www.test.com";
                doReturn(originUrl).when(urlService).getOriginUrl(shortUrl);

                //when
                final ResultActions resultActions = mockMvc.perform(get(url, shortUrl));

                //then
                verify(urlService, times(1)).getOriginUrl(shortUrl);
                resultActions.andExpect(status().isFound())
                        .andExpect(redirectedUrl(originUrl));
            }
        }
    }

    private ResultActions transformUrlPerform(final String url, final HashMap<String, Object> requestMap) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestMap)));
    }
}
