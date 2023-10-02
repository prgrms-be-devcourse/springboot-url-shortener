package com.youngurl.shortenerurl.presentation;

import com.youngurl.shortenerurl.application.UrlService;
import com.youngurl.shortenerurl.presentation.dto.UrlCreateApiRequest;
import com.youngurl.shortenerurl.presentation.dto.UrlCreateApiResponse;
import com.youngurl.shortenerurl.presentation.mapper.UrlApiMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/urls")
public class UrlRestController {
    private final String URL_PRE_FIX = "http://localhost:8080/";

    private final UrlService urlService;
    private final UrlApiMapper mapper;

    public UrlRestController(UrlService urlService, UrlApiMapper mapper) {
        this.urlService = urlService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<UrlCreateApiResponse> createUrl(@RequestBody UrlCreateApiRequest request){
        String encodedUrl = urlService.createUrl(mapper.toUrlCreateRequest(request));
        URI uri = createURI(encodedUrl);

        UrlCreateApiResponse apiResponse = UrlCreateApiResponse.from(URL_PRE_FIX + encodedUrl);

        return ResponseEntity.created(uri).body(apiResponse);
    }

    @GetMapping("/{encodedUrl}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String encodedUrl){
        String originUrl = urlService.findOriginUrl(encodedUrl);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(originUrl))
                .build();
    }

    private URI createURI(String url) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(url)
                .toUri();
    }

}
