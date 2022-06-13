package org.programmers.springbooturlshortener.web;

import lombok.Getter;
import lombok.Setter;
import org.programmers.springbooturlshortener.encoding.Encoding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UrlCreateCommandObject {

    @NotBlank
    private String original;
    @NotNull
    private Encoding encoding;
}