package org.daehwi.shorturl.controller;

import java.net.URI;

import org.daehwi.shorturl.controller.dto.ApiResponse;
import org.daehwi.shorturl.controller.dto.ResponseStatus;
import org.daehwi.shorturl.controller.dto.ShortUrlRequest;
import org.daehwi.shorturl.service.UrlService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UrlController {

	private final UrlService urlService;

	@PostMapping("/api/v1/short-url")
	public ResponseEntity<ApiResponse<String>> createShortUrl(@RequestBody @Valid ShortUrlRequest requestDto) {
		String shortUrlKey = urlService.createShortUrl(requestDto);
		URI shortUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
			.path("/" + shortUrlKey)
			.build()
			.toUri();
		return ResponseEntity.created(shortUrl).body(ApiResponse.of(ResponseStatus.OK, shortUrl.toString()));
	}

	@GetMapping("/{shortUrl}")
	public ResponseEntity<ApiResponse<Void>> getOriginUrl(@PathVariable String shortUrl) {
		String originUrl = urlService.getOriginUrl(shortUrl);
		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
			.cacheControl(CacheControl.noCache())
			.header("Location", "http://" + originUrl)
			.body(ApiResponse.of(ResponseStatus.TEMPORARY_REDIRECT));
	}
}
