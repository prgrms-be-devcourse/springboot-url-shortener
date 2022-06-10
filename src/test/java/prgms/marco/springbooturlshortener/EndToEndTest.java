package prgms.marco.springbooturlshortener;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import prgms.marco.springbooturlshortener.dto.CreateShortUrlReq;
import prgms.marco.springbooturlshortener.service.UrlService;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase
class EndToEndTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UrlService urlService;

    @Test
    @DisplayName("POST /api/v1/urls")
    void testCreateShortUrl() throws Exception {
        //given
        String originUrl = "http://www.naver.com";
        CreateShortUrlReq createShortUrlReq = new CreateShortUrlReq(originUrl);

        //when then
        mockMvc.perform(post("/api/v1/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createShortUrlReq)))
            .andExpect(status().isCreated())
            .andDo(document("post-createShortUrl",
                requestFields(
                    fieldWithPath("originUrl").type(JsonFieldType.STRING).description("원본 URL")
                ),
                responseFields(
                    fieldWithPath("shortUrl").type(JsonFieldType.STRING).description("단축 URL")
                )
            ));
    }

    @Test
    @DisplayName("GET /api/v1/urls/{shortUrl}")
    void testConvertShortUrlToOrigin() throws Exception {
        // given
        String originUrl = "http://www.google.com";
        String shortUrl = urlService.createShortUrl(originUrl);

        // when
        // then
        mockMvc.perform(get("/api/v1/urls/" + shortUrl))
            .andExpect(status().is3xxRedirection())
            .andDo(document("redirect-to-Origin"));
    }
}
