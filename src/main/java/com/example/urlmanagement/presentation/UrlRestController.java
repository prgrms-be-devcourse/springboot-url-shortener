package com.example.urlmanagement.presentation;

import com.example.urlmanagement.application.UrlService;
import com.example.urlmanagement.dto.request.CreateShortUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlRestController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> createShortUrl(@RequestBody CreateShortUrlRequest createShortUrlRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(urlService.createShortUrl(createShortUrlRequest));
    }

    @GetMapping("/shorten/{shortUrl}")
    public ResponseEntity<Void> getOriginalUrl(@PathVariable String shortUrl) {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(urlService.getOriginalUrl(shortUrl)))
                .build();
    }
}
