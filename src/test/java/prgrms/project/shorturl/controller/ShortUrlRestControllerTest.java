package prgrms.project.shorturl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import prgrms.project.shorturl.dto.ShortUrlCreateRequest;
import prgrms.project.shorturl.dto.ShortUrlRequest;
import prgrms.project.shorturl.dto.ShortUrlResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class ShortUrlRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("원본 주소를 입력받고 알고리즘에 맞는 짧은 주소로 변환한다.")
    void createShortUrl() throws Exception {
        var createRequest = new ShortUrlCreateRequest("https://google.com", "BASE62");
        var requestString = objectMapper.writeValueAsString(createRequest);

        mockMvc.perform(
            post("/api/v1/short-urls")
                .content(requestString)
                .contentType(APPLICATION_JSON)
        ).andExpect(status().isCreated())
            .andDo(
                document("short-url-create",
                    requestShortUrlCreate(),
                    responseShortUrlCreate()
            )
        );
    }

    @Test
    void requestToShortUrl() throws Exception {
        var createRequest = new ShortUrlCreateRequest("https://google.com", "BASE62");
        var createString = objectMapper.writeValueAsString(createRequest);

        var result = mockMvc.perform(post("/api/v1/short-urls").content(createString).contentType(APPLICATION_JSON)).andReturn();
        var shortUrlResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ShortUrlResponse.class);

        var shortUrlRequest = new ShortUrlRequest(shortUrlResponse.shortUrl());
        var requestString = objectMapper.writeValueAsString(shortUrlRequest);

        mockMvc.perform(
            post("/api/v1/short-urls/short-url")
                .content(requestString)
                .contentType(APPLICATION_JSON)
        ).andExpect(status().isOk())
            .andDo(
                document("short-url-request",
                    requestShortUrlRequest(),
                    responseShortUrlRequest()
            )
        );
    }

    private RequestFieldsSnippet requestShortUrlCreate() {
        return requestFields(
            fieldWithPath("originUrl").type(STRING).description("원본 주소"),
            fieldWithPath("method").type(STRING).description("주소 변환 알고리즘")
        );
    }

    private ResponseFieldsSnippet responseShortUrlCreate() {
        return responseFields(
            fieldWithPath("originUrl").type(STRING).description("원본 주소"),
            fieldWithPath("shortUrl").type(STRING).description("짧은 주소"),
            fieldWithPath("requestCount").type(NUMBER).description("요청 횟수")
        );
    }

    private RequestFieldsSnippet requestShortUrlRequest() {
        return requestFields(
            fieldWithPath("shortUrl").type(STRING).description("짧은 주소")
        );
    }

    private ResponseFieldsSnippet responseShortUrlRequest() {
        return responseFields(
            fieldWithPath("originUrl").type(STRING).description("원본 주소"),
            fieldWithPath("requestCount").type(NUMBER).description("요청 횟수")
        );
    }
}
