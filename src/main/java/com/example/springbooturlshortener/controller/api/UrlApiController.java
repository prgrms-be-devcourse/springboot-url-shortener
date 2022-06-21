package com.example.springbooturlshortener.controller.api;

import com.example.springbooturlshortener.controller.dto.UrlRequest;
import com.example.springbooturlshortener.service.UrlService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("api/v1/url")
@Validated
@RestController
public class UrlApiController {

  private final UrlService urlService;

  public UrlApiController(UrlService urlService) {
    this.urlService = urlService;
  }

  @PostMapping("/shortenUrl")
  public String shortenUrl(@Valid UrlRequest urlRequest) {
    return urlService.shortenUrl(urlRequest.getOriginalUrl());
  }

  @GetMapping("/{key}")
  public ModelAndView redirectOriginalUrl(@NotBlank @PathVariable("key") String key) {
    String originalUrl = urlService.findOriginalUrl(key);
    return new ModelAndView("redirect:" + originalUrl);
  }
}
