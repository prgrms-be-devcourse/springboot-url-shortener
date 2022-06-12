package shortUrl.shortUrl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shortUrl.shortUrl.domain.dto.CreateShortUrlDto;
import shortUrl.shortUrl.domain.dto.ShortUrlDto;
import shortUrl.shortUrl.domain.service.UrlService;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public ShortUrlDto createShortUrl(@RequestBody CreateShortUrlDto createShortUrlDto) {
//        String shortUrl = urlService.createShortUrl(createShortUrlDto);
        return null;
    }
}
