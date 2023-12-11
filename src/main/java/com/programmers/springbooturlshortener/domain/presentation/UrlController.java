package com.programmers.springbooturlshortener.domain.presentation;

import com.programmers.springbooturlshortener.domain.application.UrlService;
import com.programmers.springbooturlshortener.domain.dto.UrlControllerRequestDto;
import com.programmers.springbooturlshortener.domain.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.domain.dto.UrlServiceResponseDto;
import com.programmers.springbooturlshortener.domain.entity.EncodeType;
import com.programmers.springbooturlshortener.domain.entity.LongUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class UrlController {
    private final UrlService urlService;
    private static final String SERVER_URL = "http://localhost:8080/";

    @GetMapping
    public String home(Model model) {
        model.addAttribute("encodeTypes", EncodeType.values());
        return "home";
    }

    @PostMapping
    public String createUrl(UrlControllerRequestDto requestDto, Model model) {
        UrlServiceResponseDto responseDto = urlService.createShortUrl(UrlServiceRequestDto.builder()
                .longUrl(LongUrl.from(requestDto.getLongUrl()))
                .encodeType(requestDto.getEncodeType())
                .build()
        );
        model.addAttribute("longUrl", requestDto.getLongUrl());
        model.addAttribute("shortUrl", SERVER_URL + responseDto.getShortUrl());
        model.addAttribute("encodeTypes", EncodeType.values());
        return "home";
    }


    @GetMapping("{shortUrl}")
    public String findUrl(@PathVariable String shortUrl) {
        UrlServiceResponseDto responseDto = urlService.findLongUrl(UrlServiceRequestDto.builder().shortUrl(shortUrl).build());
        return "redirect:" + responseDto.getLongUrl();
    }
}
