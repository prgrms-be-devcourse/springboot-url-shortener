package com.programmers.urlshortener.domain.url.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.programmers.urlshortener.domain.url.application.UrlService;

@RequiredArgsConstructor
@Controller
public class UrlViewController {

	private final UrlService urlService;

	@Value("${url}")
	private String requestUrl;

	@GetMapping("/")
	public String index() {
		return "url/index";
	}

	@GetMapping("/shorts/{shortUrl}")
	public String result(@PathVariable String shortUrl, Model model) {
		String originalUrl = urlService.findOriginalUrlByShortUrl(shortUrl);

		model.addAttribute("originalUrl", originalUrl);
		model.addAttribute("shortUrlResult", requestUrl + shortUrl);
		model.addAttribute("shortUrl", shortUrl);

		return "url/result";
	}
}
