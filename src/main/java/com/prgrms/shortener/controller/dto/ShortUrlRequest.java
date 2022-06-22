package com.prgrms.shortener.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record ShortUrlRequest(@NotBlank @Pattern(regexp = URL_REGEX) String originalUrl) {

  private static final String URL_REGEX = "^(((http(s?))\\:\\/\\/)?)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]"
     + "{2,6}(:[0-9]+)?(\\/\\S*)?$";

  public ShortUrlRequest(String originalUrl) {

    this.originalUrl = originalUrl;
  }

  public String originalUrl() {

    return originalUrl;
  }
}
