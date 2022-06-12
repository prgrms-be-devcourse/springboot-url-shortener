package com.prgrms.urlshortener.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

public class CreateShortenUrlRequest {

    @URL(message = "URL 포맷이 아닙니다.")
    @NotNull(message = "URL을 입력해주세요.")
    private String originUrl;

    @NotNull(message = "인코딩 타입을 입력해주세요.")
    private String encodedType;

    private CreateShortenUrlRequest() {
    }

    public CreateShortenUrlRequest(String originUrl, String encodedType) {
        this.originUrl = originUrl;
        this.encodedType = encodedType;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getEncodedType() {
        return encodedType;
    }
}
