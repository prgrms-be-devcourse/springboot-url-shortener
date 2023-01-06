package com.example.urlshortenert2.controller;

import com.example.urlshortenert2.dto.ShortenerRequestDto;
import com.example.urlshortenert2.dto.ShortenerResponseDto;
import com.example.urlshortenert2.service.ShortenerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.*;

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

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<Object> illegalUrlHandler() {
        return ResponseEntity.badRequest()
                .body("잘못된 URL 형식입니다.(http://, https:// 를 반드시 넣어 주어야 합니다.)");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> notConnectionUrlHandler(IllegalArgumentException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<Object> notPresentDomainUrlHandler() {
        return ResponseEntity.badRequest()
                .body("존재하지 않는 도메인의 URL 입니다.");
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
        if (200 > responseCode || responseCode >= 400) {
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
