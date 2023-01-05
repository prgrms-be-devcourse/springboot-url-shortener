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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ShortenerApiController {

    private final ShortenerService shortenerService;

    public ShortenerApiController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> notFoundHandler() {
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/v1/shortener")
    public ResponseEntity<ShortenerResponseDto> createShortener(@RequestBody ShortenerRequestDto dto) {
        // TODO: 2023-01-05 이거를 return 하는걸 우리 서비스 도메인을 붙여야하는건 아닐지 생각할 것 -> 여기서 붙이는 방법이랑 리다이렉트 할때 붙여서 Location에 담는 방법
        ShortenerResponseDto shortenerUrl = shortenerService.createShortener(dto);
        return ResponseEntity.ok().body(shortenerUrl);
    }

    @GetMapping("/{shorteningKey}")
    public ResponseEntity<Object> getOriginURL(@PathVariable String shorteningKey) {
        // TODO: 2023-01-05 API 형태로 만들면 서버 간 정보를 주고 받는 건데 301을 던지는게 맞을까? => 300대 던지면 웹 브라우저가 Location으로 자동으로 리다이렉션 하는 건데 200대가 맞지 않을까?
        String originUrl = shortenerService.findByShorteningKey(shorteningKey);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY); // 301
    }
}
