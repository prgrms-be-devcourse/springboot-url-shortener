package com.prgrms.shortener.presentation;

import com.prgrms.shortener.domain.ShortenedUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HttpController {

  private final ShortenedUrlService shortenedUrlService;

  public HttpController(ShortenedUrlService shortenedUrlService) {
    this.shortenedUrlService = shortenedUrlService;
  }

  @GetMapping("/{key}")
  public String handleShortenedUrl(@PathVariable String key) {

    String originalUrl = shortenedUrlService.findOriginalUrlByKey(key);

    return "redirect:" + originalUrl;

  }

  @GetMapping("")
  public String handleMainUrl() {
    return "home";
  }

  @GetMapping("/meta")
  public String handleMetaUrl() {
    return "meta";
  }
}
