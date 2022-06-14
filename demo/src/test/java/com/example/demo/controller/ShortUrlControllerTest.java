package com.example.demo.controller;

import com.example.demo.service.ShortUrlService;
import com.example.demo.shorturlutil.ShortenAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShortUrlController.class)
class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortUrlService shortUrlService;

    @Test
    void createShortUrl() throws Exception {
        String originUrl = "https://miro.com/?utm_source=google&utm_medium=cpc&utm_" +
                "campaign=S|GOO|BRN|WW|KO-KO|Brand|Exact&utm_adgroup=&utm_custom=10501506958&" +
                "utm_content=447030169338&utm_term=miro&device=c&location=1009871&gclid=" +
                "CjwKCAjwnZaVBhA6EiwAVVyv9NZnYeotOUPYl5Dtnr-YiliYKv1dRIYA3C_FKhlnuUsbwzB8Lcx0nxoCOO8QAvD_BwE";

        String shortUrl = "ABC34";

        given(shortUrlService.createShortUrl(originUrl, ShortenAlgorithm.BASE62))
                .willReturn(shortUrl);

        mockMvc.perform(post("/url").param("url", originUrl)
                        .param("shortenAlgorithm", "BASE62"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("shortUrl", shortUrl))
                .andExpect(view().name("url/shortUrlResult"));
    }

    @Test
    void getDestinationTest() throws Exception {
        String originUrl = "https://miro.com/?utm_source=google&utm_medium=cpc&utm_" +
                "campaign=S|GOO|BRN|WW|KO-KO|Brand|Exact&utm_adgroup=&utm_custom=10501506958&" +
                "utm_content=447030169338&utm_term=miro&device=c&location=1009871&gclid=" +
                "CjwKCAjwnZaVBhA6EiwAVVyv9NZnYeotOUPYl5Dtnr-YiliYKv1dRIYA3C_FKhlnuUsbwzB8Lcx0nxoCOO8QAvD_BwE";

        String shortUrl = "ABC34";

        given(shortUrlService.getOriginUrl(shortUrl))
                .willReturn(originUrl);

        mockMvc.perform(get("/url/{shortUrl}", shortUrl))
                .andExpect(status().is3xxRedirection());
    }


}