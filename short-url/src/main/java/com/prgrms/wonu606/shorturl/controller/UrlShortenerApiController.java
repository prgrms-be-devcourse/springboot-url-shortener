package com.prgrms.wonu606.shorturl.controller;

import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateRequest;
import com.prgrms.wonu606.shorturl.controller.dto.ShortenUrlCreateResponse;
import com.prgrms.wonu606.shorturl.controller.mapper.UrlShortenerApiParamMapper;
import com.prgrms.wonu606.shorturl.controller.mapper.UrlShortenerApiResponseMapper;
import com.prgrms.wonu606.shorturl.service.UrlShortenerService;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateParam;
import com.prgrms.wonu606.shorturl.service.dto.ShortenUrlCreateResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shorten-url")
public class UrlShortenerApiController {

    private final UrlShortenerService urlShortenerService;
    private final UrlShortenerApiParamMapper paramMapper;
    private final UrlShortenerApiResponseMapper responseMapper;
    private final String baseUrl;

    public UrlShortenerApiController(
            UrlShortenerService urlShortenerService,
            UrlShortenerApiParamMapper paramMapper,
            UrlShortenerApiResponseMapper responseMapper,
            @Value("${url-shortener.base-url}") String baseUrl) {
        this.urlShortenerService = urlShortenerService;
        this.paramMapper = paramMapper;
        this.responseMapper = responseMapper;
        this.baseUrl = baseUrl;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ShortenUrlCreateResponse> getOrCreateShortenedUrl(
            @RequestBody @Valid ShortenUrlCreateRequest request) {
        ShortenUrlCreateParam param = paramMapper.toShortenUrlCreateParam(request);

        ShortenUrlCreateResult result = urlShortenerService.getOrCreateShortenUrlHash(param);
        HttpStatus httpStatus = HttpStatus.CREATED;
        if (!result.isNew()) {
            httpStatus = HttpStatus.OK;
        }

        return ResponseEntity
                .status(httpStatus)
                .body(responseMapper.toShortenUrlCreateResponse(result, baseUrl));
    }
}
