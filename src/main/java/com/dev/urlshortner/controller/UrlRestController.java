package com.dev.urlshortner.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.urlshortner.global.EncodingType;
import com.dev.urlshortner.dto.ApiResponse;
import com.dev.urlshortner.dto.UrlResponse;
import com.dev.urlshortner.dto.UrlStatsResponse;
import com.dev.urlshortner.service.UrlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UrlRestController {

	private final UrlService urlService;

	@PostMapping("/shorten")
	public ResponseEntity<ApiResponse<UrlResponse>> shortenUrl(
		@RequestParam("url") String originalUrl,
		@RequestParam(defaultValue = "BASE62") EncodingType encodingType) {
		UrlResponse response = urlService.shortenUrl(originalUrl, encodingType);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@GetMapping("/{shortKey}")
	public ResponseEntity<Void> redirect(@PathVariable String shortKey) {
		String originalUrl = urlService.getOriginalUrl(shortKey);
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
	}

	@GetMapping("/stats/{shortKey}")
	public ResponseEntity<ApiResponse<UrlStatsResponse>> getUrlStats(@PathVariable String shortKey) {
		UrlStatsResponse response = urlService.getUrlStats(shortKey);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}
}
