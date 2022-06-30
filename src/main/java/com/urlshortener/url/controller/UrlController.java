package com.urlshortener.url.controller;

import com.urlshortener.url.model.dto.CreateRequest;
import com.urlshortener.url.model.dto.CreateResponse;
import com.urlshortener.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<CreateResponse> registerUrl(@RequestBody @Valid CreateRequest request) {
        CreateResponse response = urlService.register(request);
        return ResponseEntity.ok().body(response);
    }
}
