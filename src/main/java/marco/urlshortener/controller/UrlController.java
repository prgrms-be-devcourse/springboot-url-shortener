package marco.urlshortener.controller;

import lombok.RequiredArgsConstructor;
import marco.urlshortener.dto.UrlRequest;
import marco.urlshortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/url")
    public ResponseEntity<String> makeUrl(@RequestBody UrlRequest request) {
        return ResponseEntity.ok(urlService.getShortUrl(request.longUrl()));
    }

    @GetMapping("/url/{shortUrl}")
    public ResponseEntity<String> redirectUrl(@PathVariable String shortUrl) {
        String longUrl = urlService.getLongUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, longUrl)
                .build();
    }
}
