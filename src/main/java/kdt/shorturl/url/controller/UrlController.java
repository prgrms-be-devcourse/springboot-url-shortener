package kdt.shorturl.url.controller;

import kdt.shorturl.url.dto.CreateShortUrlRequest;
import kdt.shorturl.url.dto.CreateShortenUrlResponse;
import kdt.shorturl.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/urls")
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<CreateShortenUrlResponse> findOrGenerateShortenUrl(@RequestBody CreateShortUrlRequest request) {
        CreateShortenUrlResponse response = urlService.findOrGenerateShortenUrl(request);

        HttpStatus httpStatus = response.isNew() ? HttpStatus.CREATED : HttpStatus.OK;
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(httpStatus);

        if (httpStatus == HttpStatus.CREATED) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{shortUrl}")
                    .buildAndExpand(response.shortenUrl()) // response에서 생성된 ID를 가져오는 메서드를 사용해야 함
                    .toUri();
            responseBuilder.location(location);
        }

        return responseBuilder.body(response);
    }
}
