package org.daehwi.shorturl.controller;

import lombok.RequiredArgsConstructor;
import org.daehwi.shorturl.controller.dto.ApiResponse;
import org.daehwi.shorturl.controller.dto.ResponseStatus;
import org.daehwi.shorturl.controller.dto.ShortUrlRequest;
import org.daehwi.shorturl.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/api/v1/short-url")
    public ResponseEntity<ApiResponse<String>> createShortUrl(@RequestBody ShortUrlRequest requestDto) {
        String shortUrl = urlService.createShortUrl(requestDto);
        return ResponseEntity.ok(ApiResponse.of(ResponseStatus.OK, shortUrl));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<ApiResponse<Void>> getOriginUrl(@PathVariable String shortUrl) {
        String originUrl = urlService.getOriginUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .header("Location", "http://" + originUrl)
                .body(ApiResponse.of(ResponseStatus.TEMPORARY_REDIRECT));
    }
}
