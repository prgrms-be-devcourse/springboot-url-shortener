package com.dev.shortenerurl.url.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dev.shortenerurl.url.application.UrlService;
import com.dev.shortenerurl.url.dto.response.ShortenUrlInfo;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/url")
public class UrlWebController {

	//아직 알고리즘 선택 기능은 만들지 않아서 임시로 사용
	private static final String BASE_62_ALGORITHM = "BASE_62";

	private final UrlService urlService;

	@GetMapping
	public String getUrlInputPage() {
		return "/url-input";
	}

	@PostMapping("/short")
	public String getShortenUrl(
		@RequestParam @NotNull String originUrl,
		RedirectAttributes redirectAttributes
	) {
		ShortenUrlInfo shortenUrlInfo = urlService.createShortenUrl(originUrl, BASE_62_ALGORITHM);
		redirectAttributes.addAttribute("shortenUrl", shortenUrlInfo.shortenUrl());
		redirectAttributes.addAttribute("requestCount", shortenUrlInfo.requestCount());

		return "redirect:/url/shorten_url";
	}

	@GetMapping("/shorten_url")
	public String getShortenUrlPage(
		@RequestParam String shortenUrl,
		@RequestParam long requestCount,
		Model model
	) {
		model.addAttribute("shortenUrl", shortenUrl);
		model.addAttribute("requestCount", requestCount);

		return "/shorten-url";
	}

	@GetMapping("/{encodedUrl}")
	public String redirectToOriginUrl(@PathVariable String encodedUrl) {
		String originUrl = urlService.getOriginUrl(encodedUrl).originUrl();

		return "redirect:" + originUrl;
	}
}
