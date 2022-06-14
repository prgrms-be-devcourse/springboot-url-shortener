package com.prgrms.urlshortener.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.prgrms.urlshortener.dto.CreateShortenUrlRequest;
import com.prgrms.urlshortener.dto.UrlResponse;
import com.prgrms.urlshortener.service.UrlService;

@RestController
public class UrlShortenController {

    private final UrlService urlService;

    public UrlShortenController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/")
    public ResponseEntity<Void> createShortenUrl(@RequestBody @Valid CreateShortenUrlRequest request) {
        String shortedUrl = urlService.createShortedUrl(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{shortedUrl}")
            .buildAndExpand(shortedUrl)
            .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{shortedUrl}")
    public ResponseEntity<Void> getOriginUrl(@PathVariable String shortedUrl) {
        String originUrl = urlService.getOriginUrl(shortedUrl);

        return ResponseEntity.status(HttpStatus.FOUND)
            .header("LOCATION", originUrl)
            .build();
    }

    @GetMapping("/url/{shortedUrl}")
    public ResponseEntity<UrlResponse> getUrlInformation(@PathVariable String shortedUrl) {
        return ResponseEntity.ok(urlService.getUrlInformation(shortedUrl));
    }

}
