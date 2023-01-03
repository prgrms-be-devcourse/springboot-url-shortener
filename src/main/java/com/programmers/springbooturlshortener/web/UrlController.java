package com.programmers.springbooturlshortener.web;

import com.programmers.springbooturlshortener.domain.url.UrlService;
import com.programmers.springbooturlshortener.domain.url.dto.UrlCreateDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlResponseDto;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UrlController {

	private final UrlService urlService;
	private static final String URL_PREFIX = "localhost:8080/";

	@GetMapping("/")
	public String homePage(@ModelAttribute UrlCreateDto urlCreateDto) {
		return "index";
	}

	@PostMapping("/shortener")
	public String getShortUrl(@ModelAttribute @Valid UrlCreateDto urlCreateDto, BindingResult bindingResult,
		RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "index";
		}

		UrlServiceRequestDto urlServiceRequestDto = urlCreateDto.toUrlServiceRequestDto();
		UrlResponseDto url = urlService.createShortUrl(urlServiceRequestDto);

		redirectAttributes.addAttribute("originUrl", url.originUrl());
		redirectAttributes.addAttribute("shortUrl", URL_PREFIX + url.shortUrl());
		redirectAttributes.addAttribute("requestCount", url.requestCount());

		return "redirect:/shortener";
	}

	@GetMapping("/shortener")
	public String getUrlDetail(@ModelAttribute(value = "url") UrlResponseDto url) {
		return "detail";
	}

	@GetMapping("/{shortUrl}")
	public String redirectOriginUrl(@PathVariable String shortUrl) {
		String originUrl = urlService.getOriginUrl(shortUrl).originUrl();
		return "redirect:https://" + originUrl;
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public String handleEntityNotFoundException() {
		return "error/404";
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public String handleConstraintViolationException() {
		return "error/5xx";
	}
}
