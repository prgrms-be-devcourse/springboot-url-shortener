package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.CreateShortUrlDto;
import com.example.springbootboardjpa.dto.ShortUrlDto;
import com.example.springbootboardjpa.response.ApiResponse;
import com.example.springbootboardjpa.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UrlController {
    @Autowired
    ShortUrlService urlService;

    @ExceptionHandler(value = {RuntimeException.class})
    public ApiResponse<String> error(Exception e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @GetMapping("/")
    public ModelAndView create(Pageable pageable) {
        List<ShortUrlDto> result  = urlService.readAll(pageable).get().toList();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("urls", result);
        return new ModelAndView("index", map1);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView read(@RequestParam String url) {
        CreateShortUrlDto createShortUrlDto = new CreateShortUrlDto().builder()
                .url(url)
                .build();
        urlService.create(createShortUrlDto);
        return new RedirectView("/");
    }

    @GetMapping("/{shortId}")
    public RedirectView redirect(@PathVariable String shortId) {
        ShortUrlDto result = urlService.read(shortId);
        String redirect = result.getUrl();
        return new RedirectView(redirect);
    }
}
