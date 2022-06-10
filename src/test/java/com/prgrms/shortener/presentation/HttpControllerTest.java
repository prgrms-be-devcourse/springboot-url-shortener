package com.prgrms.shortener.presentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class HttpControllerTest {

  private static final ObjectMapper json = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("url을 요청하면 축약한 url을 반환해야 한다.")
  void should_return_shortened_url() throws Exception {

    // Given
    String url = "https://github.com/epicblues/springboot-url-shortener";
    Map<String, String> requestBody = Map.of("url", url);
    String jsonBody = json.writeValueAsString(requestBody);

    // When
    ResultActions resultActions = mockMvc.perform(post("/url")
        .content(jsonBody)
        .contentType(MediaType.APPLICATION_JSON));

    // Then
    resultActions.andExpectAll(
        status().is2xxSuccessful(),
        content().contentType(MediaType.APPLICATION_JSON),
        jsonPath("url").value(Matchers.matchesRegex("^https?://[^/]+/[\\w\\d]{7}$"))
    ).andDo(document("post-url",
        requestFields(
            fieldWithPath("url").type(JsonFieldType.STRING).description("target URL")
        ),
        responseFields(
            fieldWithPath("url").type(JsonFieldType.STRING).description("shortened URL")
        )
    ));
  }
}
