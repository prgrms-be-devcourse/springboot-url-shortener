package com.young.shortenerurl.url.presentation;

import com.young.shortenerurl.url.application.UrlService;
import com.young.shortenerurl.url.application.dto.UrlInfoFindResponse;
import com.young.shortenerurl.url.presentation.dto.UrlCreateApiRequest;
import com.young.shortenerurl.url.presentation.dto.UrlCreateApiResponse;
import com.young.shortenerurl.url.presentation.dto.UrlInfoFindApiResponse;
import com.young.shortenerurl.url.presentation.mapper.UrlApiMapper;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/urls")
public class UrlRestController {
    private static final String URL_PRE_FIX = "http://localhost:8080/api/urls/";

    private final UrlService urlService;
    private final UrlApiMapper mapper;

    public UrlRestController(UrlService urlService, UrlApiMapper mapper) {
        this.urlService = urlService;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UrlCreateApiResponse> createUrl(@RequestBody @Valid UrlCreateApiRequest request) {
        String encodedUrl = urlService.createUrl(mapper.toUrlCreateRequest(request));
        UrlCreateApiResponse apiResponse = UrlCreateApiResponse.from(URL_PRE_FIX + encodedUrl);

        return ResponseEntity
                .created(URI.create(URL_PRE_FIX + encodedUrl))
                .body(apiResponse);
    }

    @GetMapping(value = "/{encodedUrl}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String encodedUrl) {
        String originUrl = urlService.findOriginUrl(encodedUrl);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(originUrl))
                .build();
    }

    @GetMapping(
            value = "/visitCount/{encodedUrl}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UrlInfoFindApiResponse> findUrlInfo(@PathVariable String encodedUrl) {
        UrlInfoFindResponse response = urlService.findUrlInfo(encodedUrl);

        UrlInfoFindApiResponse apiResponse = UrlInfoFindApiResponse.of(response, URL_PRE_FIX);

        return ResponseEntity.ok(apiResponse);
    }

}
