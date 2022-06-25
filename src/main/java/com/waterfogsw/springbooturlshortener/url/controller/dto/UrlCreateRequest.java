package com.waterfogsw.springbooturlshortener.url.controller.dto;

import com.waterfogsw.springbooturlshortener.url.entity.HashType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.web.bind.annotation.RequestParam;

public record UrlCreateRequest(
    @NotBlank
    @Pattern(regexp = URL_REGEX)
    @RequestParam(name = "orgUrl")
    String orgUrl,

    @NotNull
    @RequestParam(name = "hashType", defaultValue = "BASE64")
    HashType hashType
) {

  private static final String URL_REGEX = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]"
      + "{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
}