package com.example.urlshortenert2.controller;

import com.example.urlshortenert2.dto.ShortenerRequestDto;
import com.example.urlshortenert2.dto.ShortenerResponseDto;
import com.example.urlshortenert2.service.ShortenerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@RestController
public class ShortenerApiController {

    private final ShortenerService shortenerService;

    public ShortenerApiController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping("/api/v1/shortener")
    public ResponseEntity<ShortenerResponseDto> createShortener(@RequestBody ShortenerRequestDto dto) throws IOException {
        urlValidate(dto.url());
        ShortenerResponseDto shortenerUrl = shortenerService.createShortener(dto);
        return ResponseEntity.ok().body(shortenerUrl);
    }

    private void urlValidate(String address) throws IOException {
        URL url = new URL(address);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        int responseCode = urlConnection.getResponseCode();
        if (HttpStatus.OK.value() > responseCode || responseCode >= HttpStatus.BAD_REQUEST.value()) {
            throw new IllegalArgumentException("접속할 수 없는 URL 입니다.");
        }
    }

    @GetMapping("/{shorteningKey}")
    public ResponseEntity<Object> getOriginURL(@PathVariable String shorteningKey) {
        String originUrl = shortenerService.findByShorteningKey(shorteningKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY); // 301
    }
}
