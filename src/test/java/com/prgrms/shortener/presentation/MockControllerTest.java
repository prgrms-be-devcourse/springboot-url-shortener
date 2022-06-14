package com.prgrms.shortener.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.prgrms.shortener.domain.ShortenedUrlService;
import com.prgrms.shortener.presentation.exception.HttpExceptionHandler;
import com.prgrms.shortener.presentation.exception.HttpRestExceptionHandler;
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
class MockControllerTest {

  ShortenedUrlService shortenedUrlService = Mockito.mock(ShortenedUrlService.class);
  private MockMvc mockMvc;

  @BeforeEach
  void setup(RestDocumentationContextProvider restDocumentation) {
    mockMvc = MockMvcBuilders.standaloneSetup(
            new HttpRestController(shortenedUrlService),
            new HttpController(shortenedUrlService))
        .setControllerAdvice(new HttpRestExceptionHandler(), new HttpExceptionHandler())
        .apply(documentationConfiguration(restDocumentation)).build();
  }

  @Test
  @DisplayName("json 관련 요청에서 애플리케이션이 다루지 못하는 예외가 발생했을 때 internal server error 메시지와 상태코드가 담긴 응답을 해야 한다.")
  void internal_server_error_rest() throws Exception {

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

  @Test
  @DisplayName("페이지 요청에서 애플리케이션이 다루지 못하는 예외가 발생했을 때 internal server error 페이지를 응답해야 한다.")
  void internal_server_error_page() throws Exception {

    //when
    when(shortenedUrlService.findOriginalUrlByKey("asdfcvds")).thenThrow(new RuntimeException());
    ResultActions resultActions = mockMvc.perform(get("/asdfcvds"));

    //then
    resultActions.andExpectAll(
        status().isInternalServerError(),
        view().name("500")
    );
  }

}
