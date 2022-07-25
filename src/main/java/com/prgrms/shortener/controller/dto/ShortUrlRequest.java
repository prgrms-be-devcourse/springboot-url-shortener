package com.prgrms.shortener.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ShortUrlRequest {

  @Pattern(regexp = URL_REGEX)
  @NotBlank
  private final String originalUrl;

  private static final String URL_REGEX = "^(((http(s?))\\:\\/\\/)?)([0-9a-zA-Z\\-]+\\.)+[a-zA-Z]"
      + "{2,6}(:[0-9]+)?(\\/\\S*)?$";

  @JsonCreator
  public ShortUrlRequest(String originalUrl) {

    this.originalUrl = originalUrl;
  }

  public String getOriginalUrl() {

    return originalUrl;
  }
}
