package com.programmers.springbooturlshortener.url.presentation;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.springbooturlshortener.url.application.UrlService;
import com.programmers.springbooturlshortener.url.presentation.request.UrlCreate;
import com.programmers.springbooturlshortener.url.presentation.response.UrlResponse;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UrlRestController {

	private final UrlService urlService;

	@PostMapping("/shorten")
	@ResponseStatus(HttpStatus.CREATED)
	public UrlResponse shortenUrl(@RequestBody @Valid UrlCreate request) {
		return UrlResponse.from(urlService.create(request));
	}

	@GetMapping("/shorten")
	@ResponseStatus(HttpStatus.OK)
	public String originUrl(@RequestParam String shortUrl) {
		return urlService.getOriginUrl(shortUrl);
	}

	@GetMapping("/{shortUrl}")
	@ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
	public void redirectUrl(@PathVariable(name = "shortUrl") String shortUrl, HttpServletResponse response) throws
		IOException {
		response.sendRedirect(urlService.getOriginUrl(shortUrl));
	}
}
