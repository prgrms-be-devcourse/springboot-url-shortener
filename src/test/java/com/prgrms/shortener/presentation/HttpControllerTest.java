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
import org.junit.jupiter.api.BeforeAll;
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
  private static final String ORIGINAL_URL = "https://github.com/epicblues/springboot-url-shortener";

  private static String jsonBody;
  @Autowired
  private ShortenedUrlService urlService;

  @Autowired
  private MockMvc mockMvc;

  @BeforeAll
  private static void setup() throws JsonProcessingException {
    Map<String, String> requestBody = Map.of("url", ORIGINAL_URL);
    jsonBody = json.writeValueAsString(requestBody);
  }

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

  // TODO 1: HTML 컨텐츠를 전달하는 Controller도 restdocs를 작성해야 하는가?
  @Test
  @DisplayName("url에 path가 없을 경우 main html 페이지를 응답해야 한다.")
  void return_main_view_when_path_is_empty() throws Exception {

    mockMvc.perform(get("/")).andExpectAll(
        status().is2xxSuccessful(),
        view().name("home")
    );

  }

}
