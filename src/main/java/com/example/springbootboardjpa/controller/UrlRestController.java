package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.CreateShortUrlDto;
import com.example.springbootboardjpa.dto.ShortUrlDto;
import com.example.springbootboardjpa.response.ApiResponse;
import com.example.springbootboardjpa.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

public class UrlRestController {
    @Autowired
    ShortUrlService urlService;

    @ExceptionHandler(value = {RuntimeException.class})
    public ApiResponse<String> error(Exception e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @PostMapping(value ="/api/v1/short-url", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<ShortUrlDto> create(@RequestBody CreateShortUrlDto createUrlDto) {
        ShortUrlDto result = urlService.create(createUrlDto);
        return ApiResponse.ok(result);
    }

    @GetMapping(value = "/api/v1/short-url/{short_url}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<ShortUrlDto> get(@PathVariable String short_url) {
        ShortUrlDto result = urlService.read(short_url);
        return ApiResponse.ok(result);
    }

    @GetMapping(value = "/{short_url}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RedirectView redirect(@PathVariable String short_url) {
        ShortUrlDto result = urlService.read(short_url);
        String redirect = result.getUrl();
        return new RedirectView(redirect);
    }
}
