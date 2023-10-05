package com.progms.shorturl.controller;

import com.progms.shorturl.application.ShortUrlService;
import com.progms.shorturl.dto.UrlRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @PostMapping("/url")
    public String createShortUrl(@RequestBody @Valid UrlRequest urlRequest) {
        shortUrlService.generateUrl(urlRequest);
        return null;
    }
}
