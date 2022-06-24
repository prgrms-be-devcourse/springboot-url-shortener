package com.ryu.urlshortner.url.presentation;

import com.ryu.urlshortner.url.application.UrlService;
import com.ryu.urlshortner.url.application.dto.response.UrlDto;
import com.ryu.urlshortner.url.presentation.dto.request.UrlTransformRequest;
import com.ryu.urlshortner.url.presentation.dto.response.UrlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UrlResponse transform(@RequestBody @Valid UrlTransformRequest request) {
        final UrlDto urlResponseDto = urlService.transform(request.toDto());
        return UrlResponse.from(urlResponseDto);
    }

    @GetMapping("{shortUrl}")
    @ResponseStatus(HttpStatus.FOUND)
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        response.sendRedirect(urlService.getOriginUrl(shortUrl));
    }
}
