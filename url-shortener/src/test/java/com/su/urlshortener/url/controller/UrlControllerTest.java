package com.su.urlshortener.controller;

import com.su.urlshortener.common.SimpleExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class UrlControllerTest {
    String givenUrl = "http://ndcreplay.nexon.com/";

    @Autowired
    UrlController urlController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(urlController)
                .setControllerAdvice(SimpleExceptionHandler.class)
                .build();
    }

    @Nested
    class makeShortUrl {
        @Test
        void shotURL_을_생성할_수_있다() throws Exception {
            mockMvc.perform(post("/urls")
                            .param("algorithm", "SEQUENCE")
                            .param("originUrl", givenUrl))
                    .andExpect(handler().methodName("makeShortUrl"))
                    .andDo(print());
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "httppp://kkk.snskd", "@1dfa3"})
        void URL_형식에_맞춰서_입력하지_않으면_예외반환() throws Exception {
            var invalidUrl = "";
            mockMvc.perform(post("/urls")
                            .param("algorithm", "SEQUENCE")
                            .param("originUrl", invalidUrl))
                    .andExpect(status().isBadRequest())
                    .andExpect(forwardedUrl("error/5xx"))
                    .andDo(print());
        }
    }

    @Nested
    class urlDetails {
        @Test
        void URL_정보를_조회할_수_있다() throws Exception {
            mockMvc.perform(get("/urls/{shotToken}", "00000001"))
                    .andExpect(handler().methodName("urlDetails"))
                    .andDo(print());
        }

        @Test
        void 찾는_URL_정보가_없으면_예외가_발생한다() throws Exception {
            mockMvc.perform(get("/urls/{shotToken}", "99999999"))
                    .andExpect(handler().methodName("urlDetails"))
                    .andExpect(status().isNotFound())
                    .andExpect(forwardedUrl("error/404"))
                    .andDo(print());
        }
    }

    @Nested
    class redirectOriginUrl {
        @Test
        void 원본_URL로_리다이렉트_시킨다() throws Exception {
            mockMvc.perform(get("/{shotToken}", "00000001"))
                    .andExpect(handler().methodName("redirectOriginUrl"))
                    .andDo(print());
        }
    }

    @Nested
    class index {
        @Test
        void 메인화면() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(handler().methodName("index"))
                    .andDo(print());
        }
    }
}