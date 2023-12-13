package com.prgrms.shorturl.controller;

import com.prgrms.shorturl.dto.ShortUrlRequest;
import com.prgrms.shorturl.dto.ShortUrlResponse;
import com.prgrms.shorturl.exception.RedirectionException;
import com.prgrms.shorturl.service.ShortUrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class ShortUrlRestController {
    private final ShortUrlService shortUrlService;

    @PostMapping("/shortUrl")
    public ResponseEntity<ShortUrlResponse> getShortUrl(@RequestBody ShortUrlRequest req) {
        ShortUrlResponse res = shortUrlService.getByOriginalUrl(req);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("/{shortUrl}")
    public void findByShortUrl(@PathVariable String shortUrl, HttpServletResponse response) {
        try {
            String originalUrl = shortUrlService.getByShortUrl(shortUrl);
            log.info("get original url: " + originalUrl);
            response.sendRedirect("http://" + originalUrl);
        } catch (IOException e) {
            throw new RedirectionException("redirection 오류");
        }
    }
}
