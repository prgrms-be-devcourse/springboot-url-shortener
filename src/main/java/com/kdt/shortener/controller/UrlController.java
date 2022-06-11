package com.kdt.shortener.controller;

import com.kdt.shortener.domain.UrlForm;
import com.kdt.shortener.service.UrlInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UrlController {

    private final UrlInfoService service;

    @PostMapping("/convert")
    public String convertUrl(Model model, UrlForm urlForm) {

        String result;
        if("S".equals(urlForm.getConvertType())){
            result = service.makeShortUrl(urlForm.getUrlValue());
        }else if ("L".equals(urlForm.getConvertType())) {
            var info = service.turnOriginUrl(urlForm.getUrlValue());
            result = info.getOriginUrl();
        } else {
            throw new IllegalArgumentException();
        }

        model.addAttribute("urlForm", new UrlForm());
        model.addAttribute("result", result);
        return "index";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("urlForm", new UrlForm());

        return "index";
    }
}
