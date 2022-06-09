package com.taehan.urlshortener.controller;

import com.taehan.urlshortener.dto.UrlRequestDto;
import com.taehan.urlshortener.dto.UrlResponseDto;
import com.taehan.urlshortener.model.Url;
import com.taehan.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/convert")
    public ResponseEntity<UrlResponseDto> convertUrl(@RequestBody UrlRequestDto urlRequestDto) throws Exception {
        // url 변경 필요
        URI uri = new URI("/");

        Long saveId = urlService.save(urlRequestDto);
        Optional<Url> findUrl = urlService.findById(saveId);
        UrlResponseDto urlResponseDto = findUrl.map((url) ->
                new UrlResponseDto(url.getShortUrl())
        )
                .orElseThrow(() -> new EntityNotFoundException("저장되지 않음. Err"));
        return ResponseEntity.created(uri).body(urlResponseDto);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectOriginalUrl(@PathVariable String shortUrl) throws Exception {
        Optional<String> optionalUrl = urlService.getOriginalUrl(shortUrl);

        String originalUrl = optionalUrl.orElseThrow(() -> new EntityNotFoundException("저장된 shortUrl 이 아님"));
        URI uri = new URI(originalUrl);
        return status(HttpStatus.FOUND).location(uri).build();
    }
}
