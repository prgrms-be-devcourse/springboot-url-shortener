package com.dev.urlshortner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.urlshortner.domain.KeyEncodingGenerator;
import com.dev.urlshortner.dto.ApiResponse;
import com.dev.urlshortner.dto.UrlResponse;
import com.dev.urlshortner.dto.UrlStatsResponse;
import com.dev.urlshortner.service.UrlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UrlRestController {

	private final UrlService urlService;

	@PostMapping("/shorten")
	public ResponseEntity<ApiResponse<UrlResponse>> shortenUrl(
		@RequestParam("url") String originalUrl,
		@RequestParam(defaultValue = "BASE62") KeyEncodingGenerator keyEncodingGenerator) {
		UrlResponse response = urlService.shortenUrl(originalUrl, keyEncodingGenerator);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@GetMapping("/stats/{shortKey}")
	public ResponseEntity<ApiResponse<UrlStatsResponse>> getUrlStats(@PathVariable String shortKey) {
		UrlStatsResponse response = urlService.getUrlStats(shortKey);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}
}
