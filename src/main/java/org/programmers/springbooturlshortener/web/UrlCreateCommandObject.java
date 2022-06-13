package org.programmers.springbooturlshortener.web;

import lombok.Getter;
import lombok.Setter;
import org.programmers.springbooturlshortener.encoding.Encoding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UrlCreateCommandObject {

    @NotBlank(message = "URL을 입력해주세요.")
    private String original;
    @NotNull(message = "인코딩을 선택해주세요.")
    private Encoding encoding;
}