package kdt.shorturl.url.controller;

import jakarta.validation.Valid;
import kdt.shorturl.url.dto.CreateShortUrlRequest;
import kdt.shorturl.url.dto.CreateShortenUrlResponse;
import kdt.shorturl.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/urls")
    public ResponseEntity<CreateShortenUrlResponse> getOrGenerateShortenUrl(@Valid @RequestBody CreateShortUrlRequest request) {
        CreateShortenUrlResponse url = urlService.findOrGenerateShortenUrl(request);

        HttpStatus httpStatus = url.isNew() ? HttpStatus.CREATED : HttpStatus.OK;
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(httpStatus);

        if (httpStatus == HttpStatus.CREATED) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{shortUrl}")
                    .buildAndExpand(url.shortenUrl())
                    .toUri();
            responseBuilder.location(location);
        }
        return responseBuilder.body(url);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirectToOriginUrl(@PathVariable String shortUrl) {
        String originUrl = urlService.findOriginUrlByShortUrl(shortUrl);
        originUrl = "https://" + originUrl;

        return ResponseEntity
                .status(HttpStatus.PERMANENT_REDIRECT)
                .location(URI.create(originUrl))
                .body(originUrl);
    }
}
