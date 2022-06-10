package org.prgrms.kdt.url;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ShortUrlController.class)
class ShortUrlControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ShortUrlService service;

  @Test
  @DisplayName("홈페이지를 반환한다.")
  void testGetHomePage() throws Exception {
    mockMvc.perform(get("/")
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(view().name("shorturl"));
  }

  @Test
  @DisplayName("")
  void testShortenUrl() throws Exception {
    when(service.getShortUrl("")).thenReturn(new ShortUrl("a", "b", 0));

    mockMvc.perform(get("/")
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(view().name("shorturl"));
  }

  @Test
  @DisplayName("단축 URL이 경로로 들어오면 원본 URL로 리다이렉션한다.")
  void testRedirectUrl() throws Exception {
    when(service.getOriginalUrl("a")).thenReturn("b");

    mockMvc.perform(get("/{url}", "a")
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string(HttpHeaders.LOCATION, "b"));
  }

  @Test
  @DisplayName("단축 URL 정보를 찾을 수 없으면 404를 반환한다.")
  void testRedirectUrlThrowNotFoundException() throws Exception {
    when(service.getOriginalUrl("a")).thenThrow(NoSuchElementException.class);

    mockMvc.perform(get("/{url}", "a")
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("")
  void testGetUrlStatistics() throws Exception {
    when(service.getShortUrl("a")).thenReturn(new ShortUrl("a", "b", 0));

    mockMvc.perform(get("/{url}/statistics", "a")
            .accept(MediaType.TEXT_HTML)
        )
        .andExpect(status().isOk())
        .andExpect(view().name("shorturl"));
  }


}