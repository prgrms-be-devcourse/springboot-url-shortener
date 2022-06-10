package com.taehan.urlshortener.controller;

import com.taehan.urlshortener.dto.UrlRequestDto;
import com.taehan.urlshortener.dto.UrlResponseDto;
import com.taehan.urlshortener.model.Url;
import com.taehan.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.ResponseEntity.status;

@RestController
public class UrlController {

    private static final String URL_PROTOCOL_PREFIX = "http://";
    private static final String SLASH = "/";

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/convert")
    public ResponseEntity<UrlResponseDto> convertUrl(@RequestBody UrlRequestDto urlRequestDto)
            throws URISyntaxException {
        Long saveId = urlService.save(urlRequestDto);
        Url findUrl = urlService.findById(saveId);
        String shortUrl = findUrl.getShortUrl();

        UrlResponseDto urlResponseDto = new UrlResponseDto(shortUrl);
        URI uri = new URI(SLASH + shortUrl);
        return ResponseEntity.created(uri).body(urlResponseDto);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectOriginalUrl(@PathVariable String shortUrl) throws URISyntaxException {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        URI uri = new URI(URL_PROTOCOL_PREFIX + originalUrl);
        return status(HttpStatus.FOUND).location(uri).build();
    }
}
