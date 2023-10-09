package com.programmers.urlshortener.domain.url.presentation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.urlshortener.domain.url.application.UrlService;
import com.programmers.urlshortener.domain.url.application.dto.request.ShortUrlCreateRequest;
import com.programmers.urlshortener.domain.url.application.dto.response.ShortUrlResponse;

@RequiredArgsConstructor
@RestController
public class UrlApiController {

	private final UrlService urlService;

	@PostMapping("/api/short-urls")
	public ResponseEntity<ShortUrlResponse> createShortUrl(@Validated @RequestBody ShortUrlCreateRequest shortUrlCreateRequest) {
		ShortUrlResponse shortUrlResponse = urlService.createShortUrl(shortUrlCreateRequest);

		return ResponseEntity.ok(shortUrlResponse);
	}

	@GetMapping("/api/{shortUrl}")
	public ResponseEntity<ShortUrlResponse> findByShortUrl(@PathVariable String shortUrl) {
		ShortUrlResponse shortUrlResponse = urlService.findByShortUrl(shortUrl);

		return ResponseEntity.ok(shortUrlResponse);
	}

	@DeleteMapping("/api/{shortUrl}")
	public ResponseEntity<Void> deleteByShortUrl(@PathVariable String shortUrl) {
		urlService.deleteByShortUrl(shortUrl);

		return ResponseEntity.ok(null);
	}
}
