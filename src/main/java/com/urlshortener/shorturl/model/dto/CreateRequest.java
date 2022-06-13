package com.urlshortener.shorturl.model.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Getter
public class CreateRequest {
    @NotBlank(message = "url 값이 필요합니다.")
    @URL(message = "url 형태가 아닙니다.")
    private String url;
}
