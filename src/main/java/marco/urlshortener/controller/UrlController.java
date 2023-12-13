package marco.urlshortener.controller;

import lombok.RequiredArgsConstructor;
import marco.urlshortener.dto.UrlRequest;
import marco.urlshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/url")
    public ResponseEntity<String> makeUrl(@RequestBody UrlRequest request){
        return ResponseEntity.ok(urlService.getShortUrl(request.longUrl()));
    }
}
