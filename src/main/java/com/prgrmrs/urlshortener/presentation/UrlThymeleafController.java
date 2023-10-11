package com.prgrmrs.urlshortener.presentation;

import com.prgrmrs.urlshortener.business.UrlService;
import com.prgrmrs.urlshortener.model.UrlMapping;
import com.prgrmrs.urlshortener.model.vo.OriginalUrl;
import com.prgrmrs.urlshortener.presentation.dto.ShortenUrlResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UrlThymeleafController {

    private final UrlService service;

    public UrlThymeleafController(UrlService service) {
        this.service = service;
    }

    @GetMapping("/url-shortener")
    public String showForm() {
        return "url-shortener";
    }

    @PostMapping("/submit-url")
    public String handleSubmit(
            @ModelAttribute @Valid OriginalUrl originalUrl,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());

            return "url-shortener";
        }

        UrlMapping urlMapping = service.shortenUrl(originalUrl);
        ShortenUrlResponse response = ShortenUrlResponse.to(urlMapping);

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String fullShortenedUrl = baseUrl + "/v1/url/" + response.hash();

        model.addAttribute("shortened_url", fullShortenedUrl);

        return "display-result";
    }
}