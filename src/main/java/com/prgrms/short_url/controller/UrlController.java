package com.prgrms.short_url.controller;

import com.prgrms.short_url.dto.UrlDto;
import com.prgrms.short_url.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/{shortUrl}")
    public void redirect (
            @PathVariable String shortUrl,
            HttpServletResponse httpServletResponse) throws IOException {
        String originalUrl = urlService.redirect(shortUrl);
        httpServletResponse.sendRedirect(originalUrl);

    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<String> createShortUrl(@RequestBody UrlDto urlDto) {
        String shortUrl = urlService.createShortUrl(urlDto);
        return ResponseEntity.ok(shortUrl);
    }

}
