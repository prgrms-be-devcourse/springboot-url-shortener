package com.prgrms.shorturl.url.controller;

import com.prgrms.shorturl.url.controller.dto.ShortUrlCreatApiRequest;
import com.prgrms.shorturl.url.controller.dto.ShortUrlCreateApiResponse;
import com.prgrms.shorturl.url.controller.dto.ShortUrlFindApiResponse;
import com.prgrms.shorturl.url.controller.mapper.ShortUrlApiMapper;
import com.prgrms.shorturl.url.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api/short/")
@RestController
public class ShortUrlController {

    private final ShortUrlService shortUrlService;
    private final ShortUrlApiMapper shortUrlApiMapper;

    public ShortUrlController(ShortUrlService shortUrlService, ShortUrlApiMapper shortUrlApiMapper) {
        this.shortUrlService = shortUrlService;
        this.shortUrlApiMapper = shortUrlApiMapper;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ShortUrlCreateApiResponse> createShortUrl(
            @RequestBody @Valid ShortUrlCreatApiRequest request) {
        String shortUrl = shortUrlService.creatShortUrl(request.originUrl(), request.strategyType());
        URI uri = createURI(shortUrl);

        return ResponseEntity.created(uri).body(shortUrlApiMapper.toShortUrlCreateApiResponse(uri.toString()));
    }

    @GetMapping(value = "/{shortUrl}")
    public ResponseEntity<ShortUrlFindApiResponse> findOriginUrl(
            @PathVariable String shortUrl) {
        String originUrl = shortUrlService.getOriginUrl(shortUrl);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(originUrl))
                .body(shortUrlApiMapper.toShortUrlFindApiResponse(originUrl));
    }

    private URI createURI(String shortUrl) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{shortUrl}")
                .buildAndExpand(shortUrl)
                .toUri();
    }

}
