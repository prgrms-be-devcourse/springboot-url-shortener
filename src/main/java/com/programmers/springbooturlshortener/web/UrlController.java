package com.programmers.springbooturlshortener.web;

import com.programmers.springbooturlshortener.domain.url.UrlService;
import com.programmers.springbooturlshortener.domain.url.dto.UrlCreateDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlResponseDto;
import com.programmers.springbooturlshortener.domain.url.util.UrlValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UrlController {

	private final UrlService urlService;

	@GetMapping("/")
	public String homePage() {

		return "index";
	}

	@PostMapping("/shortener")
	public String getShortUrl(@ModelAttribute UrlCreateDto urlCreateDto) {
		UrlValidation urlValidation = new UrlValidation();

		if (urlValidation.validate(urlCreateDto.originUrl())) {
			UrlResponseDto shortUrl = urlService.createShortUrl(urlCreateDto);
			return null;
		}

		return null;
	}
}
