package org.prgrms.kdt.url;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.FOUND;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShortUrlController {

  private final ShortUrlService shortUrlService;

  private final String hostname;

  public ShortUrlController(
      ShortUrlService shortUrlService,
      @Value("${server.host}") String hostname
  ) {
    this.shortUrlService = shortUrlService;
    this.hostname = hostname;
  }

  @GetMapping
  public String getHomePage() {
    return "shorturl";
  }

  @PostMapping("/shorturl")
  public String shortenUrl(String url, String type) {
    ShortUrl shortUrl = shortUrlService.shortenUrl(url, type);
    return MessageFormat.format("redirect:{0}/statistics", shortUrl.shortUrl());
  }

  @GetMapping("/{url}")
  public ResponseEntity<Void> redirectUrl(@PathVariable String url) {
    String originalUrl = shortUrlService.getOriginalUrl(url);
    return ResponseEntity.status(FOUND).header(LOCATION, originalUrl).build();
  }

  @GetMapping("/{url}/statistics")
  public String getUrlStatistics(@PathVariable String url, Model model) {
    ShortUrl shortUrl = shortUrlService.getShortUrl(url);
    model.addAttribute("hostName", hostname);
    model.addAttribute("shortUrl", shortUrl);
    return "shorturl";
  }
}