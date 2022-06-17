package com.example.springbooturlshortener.controller.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.example.springbooturlshortener.service.UrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UrlApiController.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UrlApiControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UrlService urlService;

  private final String baseUrl = "/api/v1/url";
  private final String shortenUrl = baseUrl + "/shortenUrl";
  @Nested
  class shortenUrl메서드는 {
    @Test
    @DisplayName("원본 url를 이용해 단축 url을 생성한다")
    void 원본_url를_이용해_단축_url을_생성한다() throws Exception {
      //given
      String originalUrl = "https://programmers.co.kr/";

      //when, then
      mockMvc.perform(post(shortenUrl)
          .contentType(MediaType.APPLICATION_FORM_URLENCODED)
          .param("originalUrl", originalUrl))
        .andExpect(status().isOk());
    }
    @Nested
    class OriginalUrl이_null이거나_공백_또는_빈_값이라면 {
      @ParameterizedTest
      @NullAndEmptySource
      @ValueSource(strings = {" "})
      void BadRequest_를_응답한다(String originalUrl) throws Exception {
        //given

        //when, then
        mockMvc.perform(post(shortenUrl)
                 .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                 .param("originalUrl", originalUrl))
               .andExpect(status().isBadRequest());
      }
    }
  }
}