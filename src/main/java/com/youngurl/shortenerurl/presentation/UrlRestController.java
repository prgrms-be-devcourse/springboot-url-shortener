package com.youngurl.shortenerurl.presentation;

import com.youngurl.shortenerurl.application.UrlService;
import com.youngurl.shortenerurl.presentation.dto.UrlCreateApiRequest;
import com.youngurl.shortenerurl.presentation.dto.UrlCreateApiResponse;
import com.youngurl.shortenerurl.presentation.mapper.UrlApiMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class UrlRestController {
    private final String URL_PRE_FIX = "http://localhost:8080/";

    private final UrlService urlService;
    private final UrlApiMapper mapper;

    public UrlRestController(UrlService urlService, UrlApiMapper mapper) {
        this.urlService = urlService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/api/urls",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UrlCreateApiResponse> createUrl(@RequestBody @Valid UrlCreateApiRequest request) {
        String encodedUrl = urlService.createUrl(mapper.toUrlCreateRequest(request));
        UrlCreateApiResponse apiResponse = UrlCreateApiResponse.from(URL_PRE_FIX + encodedUrl);

        return ResponseEntity
                .created(URI.create(URL_PRE_FIX + encodedUrl))
                .body(apiResponse);
    }

    @GetMapping("/{encodedUrl}")
    public ResponseEntity<Void> redirectUrl(@PathVariable @NotBlank String encodedUrl) {
        String originUrl = urlService.findOriginUrl(encodedUrl);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(originUrl))
                .build();
    }

}
