package com.prgrms.urlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.prgrms.urlshortener.domain.dto.ShortenUrlRequest;
import com.prgrms.urlshortener.domain.dto.ShortenUrlResponse;
import com.prgrms.urlshortener.service.UrlShortenService;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

	private final UrlShortenService urlShortenService;

	@ExceptionHandler(NoSuchElementException.class)
	public String handleNoSuchElementException() {
		return "404";
	}

	@PostMapping("/shorten")
	public String shorten(@Valid @ModelAttribute ShortenUrlRequest request, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:error";
		}

		ShortenUrlResponse response = urlShortenService.generateShortUrl(request);
		model.addAttribute("shortenUrlResponse", response);

		return "shorten-result";
	}

	@GetMapping("/{shortUrl}")
	public String getOriginalUrl(@PathVariable String shortUrl) {
		log.info("shortUrl={}", shortUrl);
		return "redirect:https://" + urlShortenService.getOriginalUrl(shortUrl);
	}
}
