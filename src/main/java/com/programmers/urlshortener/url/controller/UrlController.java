package com.programmers.urlshortener.url.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.programmers.urlshortener.url.dto.UrlShortenRequest;
import com.programmers.urlshortener.url.dto.UrlShortenResponse;
import com.programmers.urlshortener.url.dto.UrlTotalClicksResponse;
import com.programmers.urlshortener.url.service.UrlService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UrlController {

	private final UrlService urlService;

	@GetMapping
	public String home() {
		return "home";
	}

	@PostMapping
	public String shortenUrl(
		@ModelAttribute final UrlShortenRequest request,
		final Model model
	) {
		log.info("{}", request);
		final UrlShortenResponse response = urlService.shortenUrl(request);
		model.addAttribute("response", response);

		return "shortenedUrl";
	}

	@GetMapping("/{shorteningKey}")
	public RedirectView goToShortenedUrl(@PathVariable final String shorteningKey) {
		log.info("shorteningKey={}", shorteningKey);
		final String originalUrl = urlService.getOriginalUrl(shorteningKey);

		return new RedirectView(originalUrl);
	}

	@GetMapping("/total-url-clicks/{shorteningKey}")
	public String countTotalClicks(
		@PathVariable final String shorteningKey,
		final Model model
	) {
		log.info("shorteningKey={}", shorteningKey);
		final UrlTotalClicksResponse response = urlService.countTotalClicks(shorteningKey);
		model.addAttribute("response", response);

		return "totalUrlClicks";
	}

	@GetMapping("/total-url-clicks")
	public String UrlClickCounter() {
		return "UrlClickCounter";
	}

	@PostMapping("/total-url-clicks")
	public String redirectTotalClicks(
		@RequestParam final String shorteningKey,
		final RedirectAttributes redirectAttributes
	) {
		log.info("shorteningKey={}", shorteningKey);
		redirectAttributes.addAttribute("shorteningKey", shorteningKey);

		return "redirect:/total-url-clicks/{shorteningKey}";
	}
}
