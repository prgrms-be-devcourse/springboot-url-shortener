package prgrms.project.shorturl.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prgrms.project.shorturl.dto.ShortUrlCreateRequest;
import prgrms.project.shorturl.dto.ShortUrlRedirectResponse;
import prgrms.project.shorturl.dto.ShortUrlRequest;
import prgrms.project.shorturl.dto.ShortUrlResponse;
import prgrms.project.shorturl.service.ShortUrlService;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/v1/short-urls")
@RequiredArgsConstructor
public class ShortUrlRestController {

    private final ShortUrlService shortUrlService;

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(@RequestBody @Validated ShortUrlCreateRequest createRequest) {
        return ResponseEntity.status(CREATED).body(shortUrlService.createShortUrl(createRequest));
    }

    @PostMapping("/short-url")
    public ResponseEntity<ShortUrlRedirectResponse> requestToShortUrl(@RequestBody ShortUrlRequest shortUrlRequest) {
        var originUrl = shortUrlService.increaseRequestCount(shortUrlRequest.shortUrl());

        return ResponseEntity.ok(originUrl);
    }
}
