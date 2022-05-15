package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.CreateShortUrlDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class UrlRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTest() throws Exception {
        CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto().builder()
                .url("https://www.naver.com/?mobile")
                .build();

        mockMvc.perform(
                post("/api/v1/short-url")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createShortUrlDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("shortUrl-create",
                                requestFields(
                                        fieldWithPath("url").type(JsonFieldType.STRING).description("url")
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                        fieldWithPath("data.shortId").type(JsonFieldType.STRING).description("shortId"),
                                        fieldWithPath("data.url").type(JsonFieldType.STRING).description("url"),
                                        fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("count"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                                )
                        )
                );
    }

    @Test
    void getTest() throws Exception {
        CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto().builder()
                .url("https://www.naver.com/?mobile")
                .build();

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/v1/short-url")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createShortUrlDto))
                )
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        JSONObject jObject = new JSONObject(result);
        JSONObject data = jObject.getJSONObject("data");
        String short_url = (String) data.get("shortId");

        mockMvc.perform(
                    get("/api/v1/short-url/{short_url}", short_url)
                 )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("shortUrl-read",
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status code"),
                                        fieldWithPath("data.shortId").type(JsonFieldType.STRING).description("shortId"),
                                        fieldWithPath("data.url").type(JsonFieldType.STRING).description("url"),
                                        fieldWithPath("data.count").type(JsonFieldType.NUMBER).description("count"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                                        )
                        )
                );
    }

    @Test
    void redirectTest() throws Exception {
        CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto().builder()
                .url("https://www.naver.com/?mobile")
                .build();

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/v1/short-url")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createShortUrlDto))
                )
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();

        JSONObject jObject = new JSONObject(result);
        JSONObject data = jObject.getJSONObject("data");
        String short_url = (String) data.get("shortId");
        String url = (String) data.get("url");

        mockMvc.perform(
                        get("/{short_url}", short_url)
                )
                .andExpect(redirectedUrl(url))
                .andDo(print())
                .andDo(document(
                        "shortUrl-redirect"
                ));
    }
}