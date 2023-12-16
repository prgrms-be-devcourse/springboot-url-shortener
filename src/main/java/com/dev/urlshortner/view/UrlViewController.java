package com.dev.urlshortner.view;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dev.urlshortner.service.UrlService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UrlViewController {

	private final UrlService urlService;

	@GetMapping
	public String index() {
		return "index";
	}

	@GetMapping("/{shortKey}")
	public ResponseEntity<Void> redirect(@PathVariable String shortKey) {
		String originalUrl = urlService.getOriginalUrl(shortKey);
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
	}
}
