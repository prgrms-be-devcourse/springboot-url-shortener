package com.prgrms.wonu606.shorturl.controller;

import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateRequest;
import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UrlShortenerApiController {

    @PostMapping(value = "/shorten-url", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortenUrlCreateResponse> generateShortenedUrl(
            @RequestBody @Valid ShortenUrlCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ShortenUrlCreateResponse(request.originalUrl()));
    }
}
