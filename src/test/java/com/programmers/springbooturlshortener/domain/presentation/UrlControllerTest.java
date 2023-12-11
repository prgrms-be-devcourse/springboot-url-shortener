package com.programmers.springbooturlshortener.domain.presentation;

import com.programmers.springbooturlshortener.domain.application.UrlService;
import com.programmers.springbooturlshortener.domain.dto.UrlControllerRequestDto;
import com.programmers.springbooturlshortener.domain.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.domain.dto.UrlServiceResponseDto;
import com.programmers.springbooturlshortener.domain.entity.Url;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.programmers.springbooturlshortener.domain.entity.EncodeType.BASE62;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UrlController.class)
@DisplayName("Test UrlController")
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UrlService urlService;

    @Test
    void testHomeView() throws Exception {
        // Arrange
        var get = get("/");
        // Act & Assert
        mockMvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void testPostMethod() throws Exception {
        // Arrange
        var post = post("/").flashAttr("UrlControllerRequestDto", new UrlControllerRequestDto("https://programmers.com", BASE62));
        // Act & Assert
        mockMvc.perform(post)
                .andExpect(status().isOk());
    }

    @Test
    void testShortUrlRedirectSuccess() throws Exception {
        // Arrange
        Url url = Url.builder()
                .longUrl("https://programmers.com")
                .encodeType(BASE62)
                .shortUrl(BASE62.encode("https://programmers.com"))
                .build();
        when(urlService.findLongUrl(any(UrlServiceRequestDto.class))).thenReturn(UrlServiceResponseDto.of(url));
        var get = get("/{shortUrl}", url.getShortUrl());
        // Act & Assert
        mockMvc.perform(get)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(url.getLongUrl()));
    }

    @Test
    void testShortUrlRedirectFail() throws Exception {
        // Arrange
        var get = get("/{shortUrl}", "temp");
        // Act & Assert
        mockMvc.perform(get)
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

}