package com.pgms.shorturlapi.url;

import com.pgms.shorturlcoredomain.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/short-url")
    public CommonResponse<String> getShortUrl(@RequestParam String url){
        String shortUrl = urlService.getShortUrl(url);

        return new CommonResponse<>(HttpStatus.OK.value(), shortUrl);
    }

    @GetMapping("/")
    public void redirectUrl(@RequestParam String shortUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        response.sendRedirect(originalUrl);
    }
}
