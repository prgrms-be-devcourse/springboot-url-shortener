package com.example.springbooturlshortener.controller.api;

import com.example.springbooturlshortener.dto.UrlRequest;
import com.example.springbooturlshortener.service.UrlService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/url")
@RestController
public class UrlApiController {

  private UrlService urlService;

  public UrlApiController(UrlService urlService) {
    this.urlService = urlService;
  }

  @PostMapping("/shortenUrl")
  public String shortenUrl(@Valid UrlRequest urlRequest) {
    return urlService.shortenUrl(urlRequest.getOriginalUrl());
  }
}
