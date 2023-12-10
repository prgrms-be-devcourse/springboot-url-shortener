package com.prgrms.shortenurl.url;

import com.prgrms.shortenurl.url.domain.Url;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/shortenUrl")
public class UrlRestController {
    private final UrlService urlService;

    public UrlRestController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(value = "/{url}")
    @ResponseBody
    public ResponseEntity<String> shortenUrl(@PathVariable String url) {
        Url shortenUrl = urlService.addLink(url);
        return ResponseEntity.ok(shortenUrl.getOriginUrl());
    }

    @GetMapping(value = "/{key}")
    @ResponseBody
    public ResponseEntity<String> getUrl(@PathVariable String key) {
        String url = urlService.getUrlByKey(key);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/redirect/{key}")
    public String redirect(@PathVariable String key) throws URISyntaxException {
        URI redirectUrl = new URI(urlService.getUrlByKey(key));
        return "redirect:" + redirectUrl;
    }
}
