package com.programmers.controller;

import com.programmers.model.CreateRequest;
import com.programmers.model.CreateResponse;
import com.programmers.service.UriService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;


@RequiredArgsConstructor
@Controller
public class UriController {
    private final UriService uriService;

    // shortUri -> uri redirect
    @GetMapping("/{shortUri}")
    public ResponseEntity<String> redirectShortUri(@PathVariable String shortUri) throws URISyntaxException {
        String originalUri = uriService.getOriginalUri(shortUri);
        URI redirectUri = new URI(originalUri);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }

    // uri -> shortUri create
    @PostMapping("/api")
    public ResponseEntity<CreateResponse> createShortUri(@RequestBody @Valid CreateRequest request) {
        CreateResponse createResponse = uriService.createShortUri(request.getUri());
        return ResponseEntity.ok(createResponse);
    }
}
