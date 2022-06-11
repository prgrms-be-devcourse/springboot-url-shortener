package com.kdt.shortener.controller;

import com.kdt.shortener.domain.UrlForm;
import com.kdt.shortener.service.UrlInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UrlController {

    private final UrlInfoService service;

    @PostMapping("/convert")
    public String convertUrl(Model model, @Valid UrlForm urlForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException();
        }

        String urlResult;
        if("S".equals(urlForm.getConvertType())){
            urlResult = service.makeShortUrl(urlForm.getUrlValue());
        }else if ("L".equals(urlForm.getConvertType())) {
            var info = service.turnOriginUrl(urlForm.getUrlValue());
            urlResult = info.getOriginUrl();
        } else {
            throw new IllegalArgumentException();
        }

        model.addAttribute("urlForm", new UrlForm());
        model.addAttribute("urlResult", urlResult);
        return "index";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("urlForm", new UrlForm());

        return "index";
    }
}
