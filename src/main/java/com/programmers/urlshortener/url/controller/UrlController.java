package com.programmers.urlshortener.url.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.urlshortener.url.dto.request.ShortUrlCreateRequest;
import com.programmers.urlshortener.url.dto.response.UrlResponse;
import com.programmers.urlshortener.url.service.UrlService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/urls")
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<UrlResponse> createShortUrl(@Valid @RequestBody ShortUrlCreateRequest shortUrlCreateRequest,
        HttpServletRequest httpServletRequest) {
        shortUrlCreateRequest.updateIp(httpServletRequest.getRemoteAddr());
        UrlResponse urlResponse = urlService.createShortUrl(shortUrlCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(urlResponse);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<UrlResponse> getShortUrlInfo(@PathVariable String shortUrl) {
        UrlResponse urlResponse = urlService.findUrlByShortUrl(shortUrl);

        return ResponseEntity.ok(urlResponse);
    }

}
