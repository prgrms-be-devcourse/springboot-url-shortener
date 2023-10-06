package com.progms.shorturl.controller;

import com.progms.shorturl.application.ShortUrlService;
import com.progms.shorturl.dto.UrlRequest;
import com.progms.shorturl.dto.UrlResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ApiShortUrlController {

    private final ShortUrlService shortUrlService;

    @PostMapping("/url")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlResponse createShortUrl(@RequestBody @Valid UrlRequest urlRequest) {
        UrlResponse urlResponse =shortUrlService.generateUrl(urlRequest);
        return urlResponse;
    }

    @GetMapping("/url/{shortUrl}")
    public void redirectUrl(@PathVariable(name = "shortUrl") String shortUrl, HttpServletResponse response) throws IOException {
        String originUrl = shortUrlService.regenerateUrl(shortUrl);
        response.sendRedirect(originUrl);
    }
}
