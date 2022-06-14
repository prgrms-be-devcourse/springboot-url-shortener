package com.kdt.shortener.controller;

import com.kdt.shortener.domain.UrlForm;
import com.kdt.shortener.service.UrlInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UrlController {

    private final UrlInfoService service;

    @PostMapping("/url/convert")
    public String convertUrl(Model model, @Valid UrlForm urlForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException();
        }

        String urlResult = service.makeShortUrl(urlForm.getUrlValue());
        model.addAttribute("urlForm", new UrlForm());
        model.addAttribute("urlResult", urlResult);
        return "shortUrl";
    }

    @GetMapping("/")
    public String startConvertPage(Model model) {
        model.addAttribute("urlForm", new UrlForm());
        return "shortUrl";
    }

    @GetMapping("/short/{url}")
    public String redirectPage(@PathVariable("url") String shortUrl) {
        String longUrl = service.turnOriginUrl(shortUrl).getOriginUrl();
        return "redirect:" + longUrl;
    }
}
