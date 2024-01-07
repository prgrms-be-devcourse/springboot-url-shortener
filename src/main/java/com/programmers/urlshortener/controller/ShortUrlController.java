package com.programmers.urlshortener.controller;

import com.programmers.urlshortener.dto.request.CreateShortUrlRequest;
import com.programmers.urlshortener.dto.response.ShortUrlResponse;
import com.programmers.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    @PostMapping("/api/v1/short-urls")
    public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody @Valid CreateShortUrlRequest request) {
        ShortUrlResponse shortUrlResponse = shortUrlService.createShortUrl(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(shortUrlResponse.shortUrl()));

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(shortUrlResponse);
    }

    @GetMapping("/{urlKey}")
    public ResponseEntity<Void> redirectToOriginUrl(@PathVariable String urlKey) {
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", shortUrlService.getOriginUrl(urlKey)).build();
    }
}
