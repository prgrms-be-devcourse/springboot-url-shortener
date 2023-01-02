package com.programmers.springbooturlshortener.web;

import com.programmers.springbooturlshortener.domain.url.UrlService;
import com.programmers.springbooturlshortener.domain.url.dto.UrlCreateDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlResponseDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.domain.url.util.UrlValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			String removedProtocolUrl = urlCreateDto.removeProtocolFromOriginUrl();
			UrlServiceRequestDto urlServiceRequestDto
					= new UrlServiceRequestDto(removedProtocolUrl, urlCreateDto.algorithm());
			UrlResponseDto shortUrl = urlService.createShortUrl(urlServiceRequestDto);
			return "index";
		}

		return "index";
	}

	@GetMapping("/shortener")
	public String getUrlDetail(@RequestParam String originUrl) {
		UrlResponseDto shortUrl = urlService.getUrlDetail(originUrl);
		return "index";
	}

	@GetMapping("/{shortUrl}")
	public String redirectOriginUrl(@PathVariable String shortUrl, RedirectAttributes redirectAttributes) {
		String originUrl = urlService.getOriginUrl(shortUrl).originUrl();
		redirectAttributes.addAttribute("originUrl", originUrl);
		return "redirect:https://{originUrl}";
	}
}
