package com.prgrmrs.urlshortener.presentation;

import static com.prgrmrs.urlshortener.exception.ErrorCode.BLANK_ORIGINAL_URL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.prgrmrs.urlshortener.business.UrlService;
import com.prgrmrs.urlshortener.exception.UrlShortenerException;
import com.prgrmrs.urlshortener.model.UrlMapping;
import com.prgrmrs.urlshortener.model.vo.OriginalUrl;
import com.prgrmrs.urlshortener.presentation.dto.ShortenUrlResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/url")
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortenUrlResponse> shortenUrl(
            @RequestBody @Valid OriginalUrl originalUrl,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UrlShortenerException(BLANK_ORIGINAL_URL);
        }

        UrlMapping retrievedUrlMapping = service.shortenUrl(originalUrl);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ShortenUrlResponse.to(retrievedUrlMapping));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> redirectURL(@PathVariable String hash) {
        OriginalUrl originalUrl = service.redirectUrl(hash);

        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, originalUrl.getValue())
                .build();
    }

}
