package com.programmers.urlshortener.url.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping("/shorten-url")
	public String shortenUrl(
		@ModelAttribute final UrlShortenRequest request,
		final Model model
	) {
		log.info("{}", request);
		final UrlShortenResponse response = urlService.shortenUrl(request);
		model.addAttribute("response", response);

		return "shortenedUrl";
	}

	@GetMapping("/shortened-url/{shorteningKey}")
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
}
