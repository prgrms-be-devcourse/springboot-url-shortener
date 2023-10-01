package com.prgrms.shorturl.domain.url.controller;

import com.prgrms.shorturl.algorithm.AlgorithmType;
import com.prgrms.shorturl.domain.url.dto.request.UrlCreateRequestDTO;
import com.prgrms.shorturl.domain.url.dto.response.UrlResponseDTO;
import com.prgrms.shorturl.domain.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @ModelAttribute("algorithmTypes")
    public AlgorithmType[] algorithmTypes() {
        return AlgorithmType.values();
    }

    @GetMapping
    public String home(@ModelAttribute("url") UrlCreateRequestDTO urlCreateRequestDTO) {
        return "index";
    }

    @PostMapping("/shorten-url")
    public String makeShortUrl(
            @ModelAttribute @Valid UrlCreateRequestDTO urlCreateRequestDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){
        if (bindingResult.hasErrors()) {
            return "index";
        }

        UrlResponseDTO urlResponseDTO = urlService.createShortUrl(urlCreateRequestDTO);
        redirectAttributes.addFlashAttribute("url",urlResponseDTO);

        return "redirect:/shorten-url";
    }

    @GetMapping("/shorten-url")
    public String getShortUrl(@ModelAttribute(value = "url") UrlResponseDTO urlResponseDTO) {
        return "shortUrlPage";
    }

    @GetMapping("/{shortUrl}")
    public String redirectOriginUrl(@PathVariable String shortUrl) {
        UrlResponseDTO originUrl = urlService.getOriginUrl(shortUrl);

        return "redirect:" + originUrl.originUrl();
    }
}
