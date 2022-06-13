package com.prgrms.shortener.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.prgrms.shortener.domain.ShortenedUrlService;
import com.prgrms.shortener.presentation.exception.HttpExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(RestDocumentationExtension.class)
class MockHttpControllerTest {

  ShortenedUrlService shortenedUrlService = Mockito.mock(ShortenedUrlService.class);
  private MockMvc mockMvc;

  @BeforeEach
  void setup(RestDocumentationContextProvider restDocumentation) {
    mockMvc = MockMvcBuilders.standaloneSetup(new HttpController(shortenedUrlService))
        .setControllerAdvice(new HttpExceptionHandler())
        .apply(documentationConfiguration(restDocumentation)).build();
  }

  @Test
  @DisplayName("애플리케이션이 다루지 못하는 예외가 발생했을 때 internal server error 메시지와 상태코드가 담긴 응답을 해야 한다.")
  void test() throws Exception {

    //when
    when(shortenedUrlService.shorten(any())).thenThrow(new RuntimeException());
    ResultActions resultActions = mockMvc.perform(post("/url")
        .content("{\"url\":\"http://naver.com\"}")
        .contentType(MediaType.APPLICATION_JSON));

    //then
    resultActions.andExpectAll(
            status().isInternalServerError(),
            jsonPath("message").value("Internal Server Error")
        ).andDo(print())
        .andDo(document("internal-server-error-json",
            responseFields(
                fieldWithPath("message").type(JsonFieldType.STRING).description("내부 서버 문제 메시지")
            )
        ))
    ;
  }

}
