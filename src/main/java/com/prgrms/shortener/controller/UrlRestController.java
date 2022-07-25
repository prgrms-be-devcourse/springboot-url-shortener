package com.prgrms.shortener.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.prgrms.shortener.controller.dto.ShortUrlRequest;
import com.prgrms.shortener.exception.ErrorMessage;
import com.prgrms.shortener.service.UrlService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlRestController {

  private final UrlService urlService;

  public UrlRestController(UrlService urlService) {

    this.urlService = urlService;
  }

  @PostMapping("api/v1/urls")
  @ResponseStatus(HttpStatus.CREATED)
  public String createShortUrl(/*@RequestBody @Valid ShortUrlRequest request*/
    @RequestBody @Valid ShortUrlRequest request) {

    return urlService.createShortUrl(request.getOriginalUrl());
  }

  @GetMapping("{uniqueKey}")
  public void redirectUrl(@PathVariable String uniqueKey, HttpServletResponse response) {

    String originalUrl = urlService.findByUniqueKey(uniqueKey);

    try {
      response.sendRedirect(originalUrl);
    } catch (IOException e) {
      throw new IllegalStateException(ErrorMessage.REDIRECT_ERROR.getMessage());
    }
  }
}
