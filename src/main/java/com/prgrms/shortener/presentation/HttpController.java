package com.prgrms.shortener.presentation;

import com.prgrms.shortener.domain.ShortenedUrlService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {

  private final ShortenedUrlService shortenedUrlService;

  public HttpController(ShortenedUrlService shortenedUrlService) {
    this.shortenedUrlService = shortenedUrlService;
  }

  @PostMapping("/url")
  public ShortenedUrlResponse shortenUrl(ShortenedUrlPayload payload, HttpServletRequest httpServletRequest) {
    String key = shortenedUrlService.shorten(payload.getUrl());
    String requestUrl = httpServletRequest.getRequestURL().toString();
    String urlWithoutPath = requestUrl.substring(0, requestUrl.length() - 3);
    return new ShortenedUrlResponse(urlWithoutPath, key);
  }
}
