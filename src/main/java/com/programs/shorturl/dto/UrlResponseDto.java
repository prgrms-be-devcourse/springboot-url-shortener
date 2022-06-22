package com.programs.shorturl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UrlResponseDto {

  private String originalUrl;

  private String shortUrl;
}
