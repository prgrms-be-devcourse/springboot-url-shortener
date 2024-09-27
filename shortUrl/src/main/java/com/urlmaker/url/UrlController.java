package com.urlmaker.url;

import com.urlmaker.dto.UrlCreateRequestDTO;
import com.urlmaker.dto.UrlCreateResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/")
    public String mainPage(@ModelAttribute UrlCreateRequestDTO urlCreateRequestDTO) {

        return "url/index";
    }

    @PostMapping("/shortenUrl")
    public String shortenUrl(
            @ModelAttribute UrlCreateRequestDTO urlCreateRequestDTO,
            RedirectAttributes redirectAttributes
    ) {
        UrlCreateResponseDTO urlCreateResponseDTO = urlService.createShortenUrl(urlCreateRequestDTO);
        redirectAttributes.addFlashAttribute("url", urlCreateResponseDTO);

        return "redirect:/shortenUrl";
    }

    @GetMapping("/shortenUrl")
    public String getShortenResult(
            @ModelAttribute UrlCreateResponseDTO urlCreateResponseDTO
    ) {

        return "url/result";
    }

    @GetMapping("/{shortenUrl}")
    public String accessOriginUrl(@PathVariable String shortenUrl) {
        String originUrl = urlService.getOriginUrl(shortenUrl).originUrl();

        return "redirect:" + originUrl;
    }

}
