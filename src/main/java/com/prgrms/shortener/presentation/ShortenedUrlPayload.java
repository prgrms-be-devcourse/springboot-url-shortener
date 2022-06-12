package com.prgrms.shortener.presentation;

import com.prgrms.shortener.presentation.validation.HttpURL;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShortenedUrlPayload {

  @HttpURL
  private String url;
}
