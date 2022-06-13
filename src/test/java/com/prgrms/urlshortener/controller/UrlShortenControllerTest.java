package com.prgrms.urlshortener.controller;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.prgrms.urlshortener.dto.CreateShortenUrlRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlShortenControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
    }

    @DisplayName("BASE62형식으로 단축 URL을 생성한다.")
    @Test
    void createShortenUrl_Base62() {
        // given
        String originUrl = "https://studyhardd.tistory.com/7612312311";
        String encodeType = "BASE62";
        CreateShortenUrlRequest request = new CreateShortenUrlRequest(originUrl, encodeType);

        // when
        ExtractableResponse<Response> response = 단축URL_생성_요청(request);

        // then
        단축URL_생성됨(response);
    }

    @DisplayName("MD5형식으로 단축 URL을 생성한다.")
    @Test
    void createShortenUrl_MD5() {
        // given
        String originUrl = "https://studyhardd.tistory.com/7612312311";
        String encodeType = "MD5";
        CreateShortenUrlRequest request = new CreateShortenUrlRequest(originUrl, encodeType);

        // when
        ExtractableResponse<Response> response = 단축URL_생성_요청(request);

        // then
        단축URL_생성됨(response);
    }

    @DisplayName("URL 포맷이 아닌 문자열로 요청하면 안된다.")
    @Test
    void createShortenUrl_InValidUrl() {
        // given
        CreateShortenUrlRequest request = new CreateShortenUrlRequest("htt://studyhard.com", "BASE64");

        // when
        ExtractableResponse<Response> response = 단축URL_생성_요청(request);

        // then
        단축URL_생성_실패(response);
    }

    @DisplayName("단축URL 요청시 원래 URL로 리다이렉트 시킨다.")
    @Test
    void redirectOriginUrl() {
        // given
        String originUrl = "https://studyhardd.tistory.com/7612312311";
        String encodeUrl = 단축URL_생성_완료(originUrl);

        // when
        ExtractableResponse<Response> response = 원본URL로_리다이렉션_요청(encodeUrl);

        // then
        원본URL로_리다이렉션됨(originUrl, response);
    }

    @DisplayName("단축URL 정보를 조회한다.")
    @Test
    void getUrl() {
        // given
        String originUrl = "https://studyhardd.tistory.com/7612312311";
        String encodeUrl = 단축URL_생성_완료(originUrl);
        원본URL로_리다이렉션_요청(encodeUrl);

        // when
        ExtractableResponse<Response> response = given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .pathParam("shortedUrl", encodeUrl)
            .when().get("/url/{shortedUrl}")
            .then().log().all().extract();

        // then
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(response.jsonPath().getString("originUrl")).isEqualTo(originUrl),
            () -> assertThat(response.jsonPath().getLong("requestCount")).isEqualTo(1)
        );
    }

    private ExtractableResponse<Response> 단축URL_생성_요청(CreateShortenUrlRequest request) {
        return given().log().all()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/")
            .then().log().all().extract();
    }

    private ExtractableResponse<Response> 원본URL로_리다이렉션_요청(String encodeUrl) {
        return given().log().all()
            .pathParam("encodeUrl", encodeUrl)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().redirects().follow(false).get("/{encodeUrl}")
            .then().log().all().extract();
    }

    private void 단축URL_생성됨(ExtractableResponse<Response> response) {
        String encodeUrl = extractShortedUrl(response);
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
            () -> assertThat(encodeUrl.length()).isLessThanOrEqualTo(8)
        );
    }

    private void 단축URL_생성_실패(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private void 원본URL로_리다이렉션됨(String originUrl, ExtractableResponse<Response> response) {
        assertAll(
            () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FOUND.value()),
            () -> assertThat(response.header("LOCATION")).isEqualTo(originUrl)
        );
    }

    private String 단축URL_생성_완료(String originUrl) {
        return extractShortedUrl(단축URL_생성_요청(new CreateShortenUrlRequest(originUrl, "BASE62")));
    }

    private String extractShortedUrl(ExtractableResponse<Response> response) {
        String responseUrl = response.header("LOCATION");
        int encodeIndex = responseUrl.lastIndexOf('/');
        return responseUrl.substring(encodeIndex + 1);
    }

}
