package com.su.urlshortener.controller;

import com.su.urlshortener.url.dto.RequestUrlDto;
import com.su.urlshortener.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UrlController {
    private final UrlService urlService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Url 생성
    @PostMapping("/urls")
    public String makeShortUrl(@ModelAttribute RequestUrlDto origin, Model model) {
        var dto = urlService.makeShortUrl(origin);
        model.addAttribute("url", dto);
        return "redirect:urls/" + dto.getShotToken();
    }

    // Url 상세
    @GetMapping("/urls/{shotToken}")
    public String urlDetails(@PathVariable String shotToken, Model model) {
        var dto = urlService.findUrlDtoByToken(shotToken);
        model.addAttribute("url", dto);
        return "urls/details";
    }

    //Url 찾기
    @GetMapping("/{shotToken}")
    public String redirectOriginUrl(@NotEmpty @PathVariable String shotToken) {
        return "redirect:" + urlService.findOriginUrlByToken(shotToken);
    }
}
