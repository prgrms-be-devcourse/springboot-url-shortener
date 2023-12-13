package com.prgrms.shorturl.controller;

import com.prgrms.shorturl.dto.UrlRequest;
import com.prgrms.shorturl.dto.UrlResponse;
import com.prgrms.shorturl.exception.RedirectionException;
import com.prgrms.shorturl.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class UrlRestController {
    private final UrlService urlService;

    @PostMapping("/shortUrl")
    public ResponseEntity<UrlResponse> getShortUrl(@RequestBody UrlRequest req) {
        HttpStatus status = urlService.isPresent(req) ? HttpStatus.OK : HttpStatus.CREATED;
        UrlResponse res = urlService.getShortUrl(req);
        return ResponseEntity.status(status).body(res);
    }

    @GetMapping("/{shortUrl}")
    public void findByShortUrl(@PathVariable String shortUrl, HttpServletResponse response) {
        try {
            String originalUrl = urlService.getOriginalUrl(shortUrl);
            log.info("get original url: " + originalUrl);
            response.sendRedirect("http://" + originalUrl);
        } catch (IOException e) {
            throw new RedirectionException("redirection 오류");
        }
    }
}
