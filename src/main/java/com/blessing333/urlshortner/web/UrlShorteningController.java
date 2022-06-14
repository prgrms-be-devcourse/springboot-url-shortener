package com.blessing333.urlshortner.web;

import com.blessing333.urlshortner.domain.application.UrlService;
import com.blessing333.urlshortner.domain.model.url.Url;
import com.blessing333.urlshortner.domain.model.url.UrlCreateCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UrlShorteningController {
    private final UrlService urlService;
    private final Converter converter = new Converter();

    @GetMapping("/")
    public String getMainView(Model model) {
        UrlCreateRequest blankRequest = new UrlCreateRequest();
        model.addAttribute("urlCreateRequest", blankRequest);
        return "index";
    }

    @GetMapping(value = {"/{id}", "/{customDomain}/{id}"})
    public String redirectOriginalUrl(@PathVariable String id) {
        Url url = urlService.loadUrlForRedirect(id);
        return "redirect:" + url.getOriginalUrl();
    }

    @GetMapping("/urls/{id}")
    public String getUrlInformationView(@PathVariable String id, Model model) {
        Url url = urlService.loadUrlById(id);
        model.addAttribute("url", url);
        return "url";
    }

    @PostMapping("/urls")
    public String registerUrl(@Valid @ModelAttribute UrlCreateRequest request, Errors errors, Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute("error", "잘못된 입력입니다.");
            model.addAttribute("urlCreateRequest", request);
            return "index";
        }

        UrlCreateCommand urlCreateCommand = converter.convert(request);
        String newUrlId = urlService.registerShortenUrl(urlCreateCommand);
        attributes.addFlashAttribute("message", "단축 Url 생성 완료");
        return "redirect:/urls/" + newUrlId;
    }
}
