package com.prgrms.wonu606.shorturl.controller;

import com.prgrms.wonu606.shorturl.service.UrlShortenerService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortUrlApiController {

    private final UrlShortenerService urlShortenerService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        String originalUrl = urlShortenerService.getOriginalUrlByShortUrl(shortUrl);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
