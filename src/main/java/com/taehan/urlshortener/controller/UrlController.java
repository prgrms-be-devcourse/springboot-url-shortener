package com.taehan.urlshortener.controller;

import com.taehan.urlshortener.dto.UrlRequestDto;
import com.taehan.urlshortener.dto.UrlResponseDto;
import com.taehan.urlshortener.model.Url;
import com.taehan.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.ResponseEntity.status;

@RestController
public class UrlController {

    private static final String URL_PROTOCOL_PREFIX = "http://";

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/convert")
    public ResponseEntity<UrlResponseDto> convertUrl(@RequestBody UrlRequestDto urlRequestDto) throws Exception {
        // url 변경 필요
        URI uri = new URI("/");

        Long saveId = urlService.save(urlRequestDto);
        Url findUrl = urlService.findById(saveId);
        UrlResponseDto urlResponseDto = new UrlResponseDto(findUrl.getShortUrl());
        return ResponseEntity.created(uri).body(urlResponseDto);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectOriginalUrl(@PathVariable String shortUrl) throws Exception {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        URI uri = new URI(URL_PROTOCOL_PREFIX + originalUrl);
        return status(HttpStatus.FOUND).location(uri).build();
    }
}
