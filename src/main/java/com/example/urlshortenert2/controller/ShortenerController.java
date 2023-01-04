package com.example.urlshortenert2.controller;

import com.example.urlshortenert2.dto.ShortenerRequestDto;
import com.example.urlshortenert2.dto.ShortenerResponseDto;
import com.example.urlshortenert2.service.ShortenerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

@RestController
public class ShortenerController {

    private final ShortenerService shortenerService;

    public ShortenerController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> notFoundHandler() {
        return ResponseEntity.notFound().build();
    }

    // TODO: POST shortener 생성
    @PostMapping("/api/v1/shortener")
    public ResponseEntity<ShortenerResponseDto> createShortener(@RequestBody ShortenerRequestDto dto) {
        // TODO: 2023-01-05 이거를 return 하는걸 우리 서비스 도메인을 붙여야하는건 아닐지 생각할 것 
        ShortenerResponseDto shortenerUrl = shortenerService.createShortener(dto);
        return ResponseEntity.ok().body(shortenerUrl);
    }

    // TODO: GET 리다이렉트 URL 제공
    @GetMapping("/{shorteningKey}")
    public ResponseEntity<Object> getOriginURL(@PathVariable String shorteningKey) {
        String originUrl = shortenerService.findByShorteningKey(shorteningKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY); // 301
    }

    // TODO: GET 입력폼 HTML 제공
}
