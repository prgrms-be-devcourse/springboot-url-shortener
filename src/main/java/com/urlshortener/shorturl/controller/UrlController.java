package com.urlshortener.shorturl.controller;

import com.urlshortener.shorturl.model.dto.CreateRequest;
import com.urlshortener.shorturl.model.dto.CreateResponse;
import com.urlshortener.shorturl.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @GetMapping("/{url}")
    public ResponseEntity<String> getTest(@PathVariable("url") String url) throws URISyntaxException {
        String originUrl = urlService.findOne(url);
        URI redirectUri = new URI(originUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @PostMapping("/api/urls")
    public ResponseEntity<CreateResponse> registerUrl(@RequestBody CreateRequest request) {
        CreateResponse response = urlService.save(request);
        return ResponseEntity.ok().body(response);
    }
}
