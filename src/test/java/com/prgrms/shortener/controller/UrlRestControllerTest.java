package com.prgrms.shortener.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.shortener.controller.dto.ShortUrlRequest;
import com.prgrms.shortener.service.UrlService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UrlRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class UrlRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UrlService urlService;

  @Nested
  @DisplayName("create 메서드는 테스트 할 때")
  class DescribeCreate {

    final String url = "/api/v1/urls";

    @Nested
    @DisplayName("url 형식에 맞지 않는 shortUrl요청이 들어오면")
    class ContextWrongUrlRequest {

      @ParameterizedTest
      @NullAndEmptySource
      @ValueSource(strings = {"apple", "never"})
      @DisplayName("400 BadRequest를 반환한다.")
      void ItReturnBadRequest(String name) throws Exception {

        final ShortUrlRequest wrongUrlRequest = new ShortUrlRequest(name);

        final MockHttpServletRequestBuilder request = post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(wrongUrlRequest));

        mockMvc.perform(request)
            .andExpect(status().isBadRequest());
      }
    }

    @Nested
    @DisplayName("url 형식에 맞게 shortUrl요청이 들어오면")
    class ContextValidRequest {

      @Test
      @DisplayName("201 Created 를 반환한다.")
      void ItReturnCreated() throws Exception {

        final String validUrl = "www.naver.com";
        final ShortUrlRequest urlRequest = new ShortUrlRequest(validUrl);

        final MockHttpServletRequestBuilder request = post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(urlRequest));

        mockMvc.perform(request)
            .andExpect(status().isCreated());

        verify(urlService).createShortUrl(any());
      }
    }
  }

  @Nested
  @DisplayName("redirect 메서드는 테스트할 때")
  class DescribeRedirect {

    @Nested
    @DisplayName("shortUrl 유효한 키를 인자로 받으면")
    class ContextWrongKey {

      @Test
      @DisplayName("302 Moved Temporarily 응답을 반환한다.")
      void itReturnMovedPermanently() throws Exception {

        final String validUrl = "http://localhost:8080/AJRJ23E";
        final String originalUrl = "originalUrl";

        when(urlService.findByUniqueKey(any())).thenReturn(originalUrl);

        mockMvc.perform(get(validUrl))
            .andExpect(status().isMovedTemporarily());

        verify(urlService).findByUniqueKey(any());
      }
    }
  }

  private String toJson(Object obj) {

    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
