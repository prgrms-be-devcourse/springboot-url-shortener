package com.prgrms.urlshortener.controller;

import com.prgrms.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlApiController {

    private final UrlService urlService;

    public UrlApiController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/v1/urls")
    public ResponseEntity<String> createShortenUrl(@RequestBody CreateShortenUrlForm form, @Value("${addr}") String address) {
        String shortenUrl = urlService.createShortenUrl(form.originUrl(), form.strategy());
        URI responseHeader = URI.create("/v1/urls" + shortenUrl);

        return ResponseEntity.created(responseHeader)
                .body(address + shortenUrl);
    }

    @GetMapping("/{shortenUrl}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortenUrl) {
        String originalUrl = urlService.getOriginalUrl(shortenUrl);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(originalUrl))
                .build();
    }
}