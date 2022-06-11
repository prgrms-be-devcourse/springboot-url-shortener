package com.sdardew.urlshortener.controller;

import com.sdardew.urlshortener.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UrlController {

  private final UrlService urlService;

  public UrlController(UrlService urlService) {
    this.urlService = urlService;
  }

  @GetMapping("/{shortUrl}")
  public String getUrl(@PathVariable String shortUrl) {
    return "redirect:" + urlService.getOriginalUrl(shortUrl);
  }
}
