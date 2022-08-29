package com.programs.shorturl.controller;

import com.programs.shorturl.dto.UrlRequestDto;
import com.programs.shorturl.dto.UrlResponseDto;
import com.programs.shorturl.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/v1")
public class UrlController {

  private final UrlService urlService;

  public UrlController(UrlService urlService) {
    this.urlService = urlService;
  }

  @GetMapping("")
  public String home() {
    return "home";
  }

  @PostMapping("")
  public String getShortUrl(UrlRequestDto urlRequestDto, Model model) {
    UrlResponseDto urlResponseDto = urlService.createShortUrl(urlRequestDto);
    model.addAttribute("urlResponseDto", urlResponseDto);
    return "result";
  }

  @GetMapping("/{shortUrl}")
  public String redirectOriginalUrl(@PathVariable String shortUrl) {
    String originalUrl = urlService.getOriginalUrl(shortUrl);
    return "redirect:" + originalUrl;
  }
}
