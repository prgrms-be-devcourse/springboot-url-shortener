package com.pgms.shorturlapi.url;

import com.pgms.shorturlcoredomain.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/short-url")
    public CommonResponse<String> getShortUrl(@RequestParam(value = "algorithm", required = false, defaultValue = "base62Converter") String algorithm,
                                              HttpServletRequest request){

        String url = request.getParameter("url");
        String shortUrl = urlService.getShortUrl(url, algorithm);

        StringBuffer sb = new StringBuffer(request.getRequestURL().toString().replace(request.getRequestURI(), ""));
        sb.append("/").append(shortUrl);

        return new CommonResponse<>(HttpStatus.OK.value(), sb.toString());
    }

    @GetMapping("/{shortUrl}")
    public void redirectUrl(@PathVariable String shortUrl, HttpServletResponse response) throws URISyntaxException, IOException {
        String originalUrl = urlService.getOriginalUrl(shortUrl);

        URI uri = new URI(originalUrl);
        String encodedUrl = uri.toASCIIString();

        response.sendRedirect(encodedUrl);
    }
}
