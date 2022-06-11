package com.sdardew.urlshortener.controller.api;

import com.sdardew.urlshortener.dto.CreateUrlRequestDto;
import com.sdardew.urlshortener.dto.CreateUrlResponseDto;
import com.sdardew.urlshortener.service.UrlService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UrlRestController {

  private final UrlService urlService;

  public UrlRestController(UrlService urlService) {
    this.urlService = urlService;
  }

  @PostMapping("/service/create")
  public CreateUrlResponseDto createUrl(@RequestBody CreateUrlRequestDto requestDto) throws IOException {
    return urlService.createUrl(requestDto);
  }

}
