package com.programmers.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

@Getter
public class CreateRequest {
    @NotBlank(message = "URL 값이 필요합니다.")
    @URL(message = "URL 형태가 아닙니다.")
    private String uri;
}
