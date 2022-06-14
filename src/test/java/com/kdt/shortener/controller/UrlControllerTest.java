package com.kdt.shortener.controller;

import com.kdt.shortener.domain.UrlForm;
import com.kdt.shortener.domain.UrlInfo;
import com.kdt.shortener.service.UrlInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UrlControllerTest {

    @Mock
    private UrlInfoService service;

    @InjectMocks
    private UrlController controller;
    private MockMvc mockMvc;

    String longUrl = "https://app.gather.town/app/";

    String hash = "5Aa";
    String shortUrl = "http://localhost:8080/short/" + hash;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(ControllerExceptionHandler.class)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("Main Page 테스트")
    void startPageTest() throws Exception {

        //given
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("urlForm"))
                .andExpect(view().name("shortUrl"));
    }

    @Test
    @DisplayName("convert URL 컨트롤러 테스트")
    void convertUrlTest() throws Exception {

        //given
        var urlForm = new UrlForm();
        urlForm.setUrlValue(longUrl);
        when(service.makeShortUrl(longUrl)).thenReturn(shortUrl);

        mockMvc.perform(post("/url/convert")
                        .flashAttr("urlForm", urlForm))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("urlResult"))
                .andExpect(view().name("shortUrl"));
    }

    @Test
    @DisplayName("ShortURL redirect 테스트")
    void redirectUrlPageTest() throws Exception {

        //given
        var urlInfo = new UrlInfo(longUrl);
        when(service.turnOriginUrl(hash)).thenReturn(urlInfo);

        mockMvc.perform(get(shortUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(longUrl));
    }

    @Test
    @DisplayName("잘못된 shortURL 입력 테스트")
    void wrongShortUrlPageTest() throws Exception {

        //given
        String wrongHash = "WRONGURL";
        String wrongUrl = "http://localhost:8080/short/" + wrongHash;
        when(service.turnOriginUrl(wrongHash)).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get(wrongUrl))
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass().getCanonicalName(),
                                is(equalTo(IllegalArgumentException.class.getCanonicalName()))))
                .andExpect(view().name("/error/404"));
    }
}