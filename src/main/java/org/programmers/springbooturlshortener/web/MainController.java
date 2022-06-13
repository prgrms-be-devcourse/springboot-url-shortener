package org.programmers.springbooturlshortener.web;

import lombok.RequiredArgsConstructor;
import org.programmers.springbooturlshortener.encoding.Encoding;
import org.programmers.springbooturlshortener.repository.DuplicateUrlException;
import org.programmers.springbooturlshortener.service.UrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UrlService urlService;

    @GetMapping("/")
    public String mainPage(Model model) {
        UrlCreateCommandObject url = new UrlCreateCommandObject();
        model.addAttribute("url", url);

        Encoding[] encodings = Encoding.values();
        model.addAttribute("encodings", encodings);

        return "main";
    }

    @PostMapping("/")
    public String createCommand(@Valid @ModelAttribute("url") UrlCreateCommandObject url, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        Encoding[] encodings = Encoding.values();
        model.addAttribute("encodings", encodings);

        try {
            if (!bindingResult.hasErrors()) {
                String shortenUrl = urlService.newUrl(url.getOriginal(), url.getEncoding());
                redirectAttributes.addFlashAttribute("shortenUrl", shortenUrl);
                return "redirect:";
            }
        } catch (DuplicateUrlException e) {
            bindingResult.reject("url.duplicate", new Object[]{url.getOriginal()}, "이미 저장된 url입니다.");
            return "main";
        }
        return "main";
    }

    @GetMapping("/{shortenUrl}")
    public String redirectToOriginalUrl(@PathVariable String shortenUrl) {
        String originalUrl = urlService.getOriginalUrl(shortenUrl);
        return "redirect:" + originalUrl;
    }
}