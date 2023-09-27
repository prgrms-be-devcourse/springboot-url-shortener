package com.urlMaker.shortUrl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Slf4j
@Controller
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @GetMapping("/")
    public String mainPage(@ModelAttribute @Valid UrlCreateRequestDTO urlCreateRequestDTO){

        return "index";
    }

    @PostMapping( "/shortenUrl")
    public String shortenUrl(
            @ModelAttribute UrlCreateRequestDTO urlCreateRequestDTO,
            RedirectAttributes redirectAttributes,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            log.error("error");

            return "index";
        }

        UrlCreateResponseDTO urlCreateResponseDTO = urlService.createShortenUrl(urlCreateRequestDTO);
        redirectAttributes.addFlashAttribute("url",urlCreateResponseDTO);

        return "redirect:/shortenUrl";
    }

    @GetMapping("/shortenUrl")
    public String getShortenResult(
            @ModelAttribute("url") UrlCreateResponseDTO urlCreateResponseDTO
    ) {

        return "result";
    }

    @GetMapping("{shortenUrl}")
    public String accessOriginUrl(@PathVariable String shortenUrl){
        String originUrl = urlService.getOriginUrl(shortenUrl).originUrl();

        return "redirect:" + originUrl;
    }

}
