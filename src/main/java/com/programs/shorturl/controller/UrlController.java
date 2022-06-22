package com.programs.shorturl.controller;

import com.programs.shorturl.dto.UrlRequestDto;
import com.programs.shorturl.dto.UrlResponseDto;
import com.programs.shorturl.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UrlController {

  private final UrlService urlService;
  private static final Logger log = LoggerFactory.getLogger(UrlController.class);

  public UrlController(UrlService urlService) {
    this.urlService = urlService;
  }

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @PostMapping("/")
  public String getShortUrl(UrlRequestDto urlRequestDto, Model model) {
    UrlResponseDto urlResponseDto = urlService.createShortUrl(urlRequestDto);
    model.addAttribute("urlResponseDto", urlResponseDto);
    return "result";
  }

  @GetMapping("/{shortUrl}")
  public String redirectOriginalUrl(@PathVariable String shortUrl) {
    String originalUrl = urlService.getOriginalUrl(shortUrl);
    return "redirect:" + "http://" + originalUrl;
  }
}
