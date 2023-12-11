package com.pgms.shorturlapi.url;

import com.pgms.shorturlcoredomain.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/short-url")
    public CommonResponse<String> getShortUrl(@RequestParam String url, HttpServletRequest request){
        String shortUrl = urlService.getShortUrl(url);

        StringBuffer sb = new StringBuffer(request.getRequestURL().toString().replace(request.getRequestURI(), ""));
        sb.append("/").append(shortUrl);

        return new CommonResponse<>(HttpStatus.OK.value(), sb.toString());
    }

    @GetMapping("/{shortUrl}")
    public void redirectUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        response.sendRedirect(originalUrl);
    }
}
