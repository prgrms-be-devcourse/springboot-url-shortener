package com.programmers.urlshortener.domain.url.presentation;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.programmers.urlshortener.domain.url.application.UrlService;
import com.programmers.urlshortener.domain.url.application.dto.response.ShortUrlResponse;

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

	@GetMapping("/details/{shortUrl}")
	public String details(@PathVariable String shortUrl, Model model) {
		ShortUrlResponse shortUrlResponse = urlService.findByShortUrl(shortUrl);

		model.addAttribute("shortUrlResult", requestUrl + shortUrl);
		model.addAttribute("url", shortUrlResponse);

		return "url/detail";
	}

	@GetMapping("/{shortUrl}")
	public void accessShortUrl(@PathVariable String shortUrl, HttpServletResponse httpServletResponse) throws IOException {
		String originalUrl = urlService.findOriginalUrlByShortUrl(shortUrl);

		urlService.updateRequestCount(shortUrl);

		httpServletResponse.sendRedirect(originalUrl);
	}
}
