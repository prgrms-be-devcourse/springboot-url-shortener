package com.jhs.shortenerurl.controller;

import com.jhs.shortenerurl.controller.dto.UrlRequest;
import com.jhs.shortenerurl.service.UrlService;
import com.jhs.shortenerurl.service.convert.Mode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenUrlController {

    private final UrlService urlService;

    public ShortenUrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/v1/shorten")
    public String shorten(@RequestBody UrlRequest request) {
        return urlService.shorten(request.url(), Mode.valueOf(request.shortenMode().toUpperCase()));
    }

}
