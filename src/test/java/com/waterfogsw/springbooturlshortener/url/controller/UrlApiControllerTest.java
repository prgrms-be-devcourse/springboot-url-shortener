package com.waterfogsw.springbooturlshortener.url.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waterfogsw.springbooturlshortener.url.entity.HashType;
import com.waterfogsw.springbooturlshortener.url.serivce.UrlService;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = UrlApiController.class)
@MockBeans({@MockBean(JpaMetamodelMappingContext.class)})
@AutoConfigureRestDocs
class UrlApiControllerTest {

  private static final String API_URL = "/api/v1/short-url";

  @MockBean
  private UrlService urlService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper mapper;

  @Nested
  @DisplayName("create 메서드는")
  class DescribeCreate {

    @Nested
    @DisplayName("유효한 url 값과, HashType 이 전달되면")
    class ContextWithValidUrlAndHashType {

      @ParameterizedTest
      @EnumSource(HashType.class)
      @DisplayName("Created 를 응답한다")
      void ItResponseCreated(HashType hashType) throws Exception {
        //given
        final var testUrl = "https://www.naver.com";
        final var requestMap = new HashMap<String, Object>();
        requestMap.put("orgUrl", testUrl);
        requestMap.put("hashType", hashType.name());

        final var requestBody = mapper.writeValueAsString(requestMap);

        //when
        final var request = MockMvcRequestBuilders.post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        final var response = mockMvc.perform(request);

        //then
        verify(urlService).shorten(anyString(), any(HashType.class));
        response.andExpect(status().isCreated())
            .andDo(document("Create short-url",
                requestFields(
                    fieldWithPath("orgUrl")
                        .type(JsonFieldType.STRING)
                        .description("단축할 원본 URL"),
                    fieldWithPath("hashType")
                        .type(JsonFieldType.STRING)
                        .description("Short URL 을 생성하는 알고리즘")
                )
            ));
      }
    }

    @Nested
    @DisplayName("유효하지 않은 URL 형식이 입력되면")
    class ContextWithInvalidUrl {

      @ParameterizedTest
      @NullAndEmptySource
      @ValueSource(strings = {"test", "hello"})
      @DisplayName("BadRequest 를 응답한다")
      void ItResponseBadRequest(String testUrl) throws Exception {
        //given
        final var requestMap = new HashMap<String, Object>();
        requestMap.put("orgUrl", testUrl);
        requestMap.put("hashType", "SHA256");

        final var requestBody = mapper.writeValueAsString(requestMap);

        //when
        final var request = MockMvcRequestBuilders.post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        final var response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest());
      }
    }

    @Nested
    @DisplayName("유효하지 않은 HashType 이 입력되면")
    class ContextWithInvalidHashType {

      @ParameterizedTest
      @NullAndEmptySource
      @ValueSource(strings = {"test", "hello"})
      @DisplayName("BadRequest 를 응답한다")
      void ItResponseBadRequest(String hashType) throws Exception {
        //given
        final var requestMap = new HashMap<String, Object>();
        requestMap.put("orgUrl", "https://www.naver.com");
        requestMap.put("hashType", hashType);

        final var requestBody = mapper.writeValueAsString(requestMap);

        //when
        final var request = MockMvcRequestBuilders.post(API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody);

        final var response = mockMvc.perform(request);

        //then
        response.andExpect(status().isBadRequest());
      }
    }

  }
}