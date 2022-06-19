package com.waterfogsw.springbooturlshortener.url.controller;

import com.waterfogsw.springbooturlshortener.url.controller.dto.UrlCreateRequest;
import com.waterfogsw.springbooturlshortener.url.serivce.UrlService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/short-url")
public class UrlApiController {

  private final UrlService urlService;

  public UrlApiController(UrlService urlService) {
    this.urlService = urlService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String create(@Valid @RequestBody UrlCreateRequest request) {
    return urlService.shorten(request.orgUrl(), request.hashType());
  }

}
