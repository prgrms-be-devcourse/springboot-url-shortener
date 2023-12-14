package org.daehwi.shorturl.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.daehwi.shorturl.controller.dto.ApiResponse;
import org.daehwi.shorturl.controller.dto.ResponseStatus;
import org.daehwi.shorturl.controller.dto.ShortUrlRequest;
import org.daehwi.shorturl.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;
    private final String DEFAULT_SCHEME = "http://";
    private final String HOST = "localhost:8080/";

    @PostMapping("/api/v1/short-url")
    public ResponseEntity<ApiResponse<String>> createShortUrl(@RequestBody @Valid ShortUrlRequest requestDto) throws URISyntaxException {
        URI shortUrl = new URI(DEFAULT_SCHEME + HOST + urlService.createShortUrl(requestDto));
        return ResponseEntity.created(shortUrl).body(ApiResponse.of(ResponseStatus.OK, shortUrl.toString()));
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<ApiResponse<Void>> getOriginUrl(@PathVariable String shortUrl) {
        String originUrl = urlService.getOriginUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .header("Location", "http://" + originUrl)
                .body(ApiResponse.of(ResponseStatus.TEMPORARY_REDIRECT));
    }
}
