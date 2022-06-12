package com.kdt.shortener.domain;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Data
public class UrlForm {
    @NotBlank
    private String urlValue;
}
