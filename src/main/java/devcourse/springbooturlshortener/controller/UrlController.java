package devcourse.springbooturlshortener.controller;

import devcourse.springbooturlshortener.dto.ShortUrlCreateRequest;
import devcourse.springbooturlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/api/v1/urls/{shortUrl}")
    public String getOriginalUrlAndIncreaseHit(@PathVariable(name = "shortUrl") String shortUrl) {
        return this.urlService.getOriginalUrlAndIncreaseHit(shortUrl);
    }

    @PostMapping("/api/v1/urls")
    public String createShortUrl(@RequestBody ShortUrlCreateRequest request) {
        return this.urlService.createShortUrl(request);
    }
}
