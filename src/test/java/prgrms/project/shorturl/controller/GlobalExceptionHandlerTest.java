package prgrms.project.shorturl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import prgrms.project.shorturl.dto.ShortUrlCreateRequest;
import prgrms.project.shorturl.dto.ShortUrlRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class GlobalExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("형식에 맞지 않는 Url 이 들어올 경우 예외를 처리한다.")
    void testHandleMethodArgumentNotValid() throws Exception {
        var createRequest = new ShortUrlCreateRequest("asgagweaweg", "BASE62");
        var requestString = objectMapper.writeValueAsString(createRequest);

        mockMvc.perform(post("/api/v1/short-urls").content(requestString).contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(document("short-url_invalid-value",
                responseFieldsSnippet()
                )
            );
    }

    @Test
    @DisplayName("지원하지 않는 미디어 타입을 요청하는 경우 예외를 처리한다.")
    void testHandleHttpMediaTypeNotSupported() throws Exception {
        var createRequest = new ShortUrlCreateRequest("https://google.com", "BASE62");
        var requestString = objectMapper.writeValueAsString(createRequest);

        mockMvc.perform(post("/api/v1/short-urls").content(requestString).contentType(APPLICATION_PDF))
            .andExpect(status().isUnsupportedMediaType())
            .andDo(document("short-url_unsupported-media-type",
                responseFieldsSnippet()
                )
            );
    }

    @Test
    @DisplayName("엔티티를 찾지 못하는 경우 예외를 처리한다.")
    void testHandleNoSuchElementException() throws Exception {
        var shortUrlRequest = new ShortUrlRequest("rasdfjklaweghpaguiw");
        var requestString = objectMapper.writeValueAsString(shortUrlRequest);

        mockMvc.perform(post("/api/v1/short-urls/short-url").content(requestString).contentType(APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(document("short-url_not-found",
                responseFieldsSnippet()
                )
            );
    }

    private ResponseFieldsSnippet responseFieldsSnippet() {
        return responseFields(
            fieldWithPath("message").type(STRING).description("에러 메시지"),
            fieldWithPath("cause").type(STRING).description("에러 원인")
        );
    }
}