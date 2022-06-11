package com.kdt.shortener.domain;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UrlForm {
    private String urlValue;
    private String convertType;
}
