package com.example.shorturl.controller.api;

import com.example.shorturl.dto.request.ShortUrlCreateRequest;
import com.example.shorturl.dto.response.OriginUrlResponse;
import com.example.shorturl.dto.response.ShortUrlResponse;
import com.example.shorturl.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ApiUrlController {

    private final UrlService urlService;

    @PostMapping("/url")
    public ResponseEntity<ShortUrlResponse> getOrGenerateShortUrl(@Valid  @RequestBody ShortUrlCreateRequest shortUrlCreateRequest) {
        ShortUrlResponse shortUrl = urlService.createOrGetShortUrl(shortUrlCreateRequest);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException, URISyntaxException {
        OriginUrlResponse originUrlResponse = urlService.getOriginUrl(shortUrl);
        URI uri = new URI(originUrlResponse.originUrl());
        response.sendRedirect(uri.toASCIIString());
    }
}
