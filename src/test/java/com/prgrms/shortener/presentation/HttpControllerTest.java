package com.prgrms.shortener.presentation;

import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.shortener.domain.ShortenedUrlService;
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

  private static final ObjectMapper json;
  private static final String ORIGINAL_URL;
  private static final Map<String, String> requestBody;
  private static final String jsonBody;

  static {
    try {
      json = new ObjectMapper();
      ORIGINAL_URL = "https://github.com/epicblues/springboot-url-shortener";
      requestBody = Map.of("url", ORIGINAL_URL);
      jsonBody = json.writeValueAsString(requestBody);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Autowired
  private ShortenedUrlService urlService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("url을 요청하면 축약한 url을 반환해야 한다.")
  void should_return_shortened_url() throws Exception {

    // Given

    // When
    ResultActions resultActions = mockMvc.perform(post("/url")
        .content(jsonBody)
        .contentType(MediaType.APPLICATION_JSON));

    // Then
    resultActions.andExpectAll(
        status().is2xxSuccessful(),
        content().contentType(MediaType.APPLICATION_JSON),
        jsonPath("url").value(Matchers.matchesRegex("^https?://[^/\\s]+/[\\w\\d]{7}$"))
    ).andDo(document("post-url",
        requestFields(
            fieldWithPath("url").type(JsonFieldType.STRING).description("target URL")
        ),
        responseFields(
            fieldWithPath("url").type(JsonFieldType.STRING).description("shortened URL")
        )
    ));
  }

  @Test
  @DisplayName("원본 url이 url 형식에 맞지 않으면, 400 Bad Request가 담긴 응답을 보내야 한다.")
  void should_return_400_bad_request_response_when_request_url_is_wrong() throws Exception {

    // Given
    String wrongPayload = json.writeValueAsString(Map.of("url", "asdkhvczlvdf"));

    // When
    ResultActions actions = mockMvc.perform(post("/url")
        .content(wrongPayload)
        .contentType(MediaType.APPLICATION_JSON));

    // Then
    actions.andExpectAll(
        status().isBadRequest(),
        jsonPath("message", Matchers.equalTo("잘못된 형식의 URL 문자열입니다."))
    ).andDo(document("post-url-wrong-payload", requestFields(
        fieldWithPath("url").type(JsonFieldType.STRING).description("Wrong url format")
    ), responseFields(
        fieldWithPath("message").type(JsonFieldType.STRING).description("Error description")
    )));
  }

  @Test
  @DisplayName("저장되어 있는 축약된 url 요청이 들어오면, 원본 url로 성공적으로 redirect할 수 있어야 한다.")
  void redirect_to_original_url_successfully() throws Exception {

    // Given(먼저 서버에 url을 저장해야 한다.)

    String savedKey = urlService.shorten(ORIGINAL_URL);

    // When
    ResultActions results = mockMvc.perform(get("/" + savedKey));

    // Then
    results.andExpect(redirectedUrl(ORIGINAL_URL))
        .andDo(print())
        .andDo(document("url-redirect-success", httpResponse()));

  }

  @Test
  @DisplayName("요청한 축약 url이 서버에 저장되어 있지 않으면, 404 상태 코드와 실패 페이지 view를 응답해야 한다.")
  void redirect_to_failure_page_when_url_is_not_registered() throws Exception {

    // When
    ResultActions result = mockMvc.perform(get("/aabb09c"));

    // Then
    result.andExpectAll(
        view().name("failure"),
        status().isNotFound()
    ).andDo(document("url-redirect-failure"));

  }

  @Test
  @DisplayName("url에 path가 없을 경우 main html 페이지를 응답해야 한다.")
  void return_main_view_when_path_is_empty() throws Exception {

    mockMvc.perform(get("/")).andExpectAll(
        status().is2xxSuccessful(),
        view().name("home")
    );

  }

}
