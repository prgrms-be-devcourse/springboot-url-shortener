package com.prgrms.shortener.presentation;

import com.prgrms.shortener.domain.ShortenedUrlService;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HttpController {

  private final ShortenedUrlService shortenedUrlService;

  public HttpController(ShortenedUrlService shortenedUrlService) {
    this.shortenedUrlService = shortenedUrlService;
  }

  @PostMapping("/url")
  @ResponseBody
  public ShortenedUrlResponse shortenUrl(ShortenedUrlPayload payload, HttpServletRequest httpServletRequest) {
    String key = shortenedUrlService.shorten(payload.getUrl());
    String requestUrl = httpServletRequest.getRequestURL().toString();
    String urlWithoutPath = requestUrl.substring(0, requestUrl.length() - 3);
    return new ShortenedUrlResponse(urlWithoutPath, key);
  }

  @GetMapping("/{key}")
  public String handleShortenedUrl(@PathVariable String key) {

    Optional<String> originalUrl = shortenedUrlService.findOriginalUrlByKey(key);

    return originalUrl.map(url -> "redirect:" + url).orElseThrow(ShortenedUrlNotFoundException::new);

  }
}
