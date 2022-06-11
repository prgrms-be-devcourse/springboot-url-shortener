package com.kdt.shortener.domain;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Data
public class UrlForm {
    @NotBlank(message = "URL 값을 비워둘 수 없습니다.")
    private String urlValue;

}
