package com.programmers.springbooturlshortener.web;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.programmers.springbooturlshortener.domain.url.UrlService;
import com.programmers.springbooturlshortener.domain.url.dto.UrlServiceRequestDto;
import com.programmers.springbooturlshortener.web.dto.UrlCreateDto;
import com.programmers.springbooturlshortener.web.dto.UrlResponseDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UrlController {

	private final UrlService urlService;

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
		UrlResponseDto urlResponseDto = urlService.createShortUrl(urlServiceRequestDto);

		redirectAttributes.addFlashAttribute("url", urlResponseDto);

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
