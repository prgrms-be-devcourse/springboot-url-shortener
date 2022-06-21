package com.waterfogsw.springbooturlshortener.url.controller;

import com.waterfogsw.springbooturlshortener.common.exception.NotFoundException;
import com.waterfogsw.springbooturlshortener.url.serivce.UrlService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlRedirectController {

  private static final Logger log = LoggerFactory.getLogger(UrlRedirectController.class);
  private final UrlService urlService;

  public UrlRedirectController(UrlService urlService) {
    this.urlService = urlService;
  }

  @GetMapping("{shortKey}")
  public void redirect(
      HttpServletResponse response,
      @PathVariable @NotEmpty String shortKey
  ) {
    final var redirectUri = urlService.getRedirectUrl(shortKey);

    try {
      response.sendRedirect(redirectUri);
    } catch (IOException e) {
      log.warn(e.getMessage());
    }
  }
}
