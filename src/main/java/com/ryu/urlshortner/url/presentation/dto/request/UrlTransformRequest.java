package com.ryu.urlshortner.url.presentation.dto.request;

import com.ryu.urlshortner.common.validator.URLValid;
import com.ryu.urlshortner.url.application.dto.request.UrlTransformDto;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class UrlTransformRequest {

    @NotBlank
    @URL
    @URLValid
    private String originUrl;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expiredAt;

    private UrlTransformRequest() {
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public UrlTransformDto toDto() {
        return UrlTransformDto.builder()
                .originUrl(originUrl)
                .expiredAt(expiredAt)
                .build();
    }
}
