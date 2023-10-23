package com.seungwon.springbooturlshortener.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seungwon.springbooturlshortener.application.UrlShortenerService;
import com.seungwon.springbooturlshortener.application.dto.UrlCreateRequest;
import com.seungwon.springbooturlshortener.application.dto.UrlCreateResponse;
import com.seungwon.springbooturlshortener.domain.RequestLimit;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/urls")
public class UrlController {

	private final UrlShortenerService urlService;

	private final RequestLimit requestLimit;

	@PostMapping
	public ResponseEntity<UrlCreateResponse> urlSave(@Validated @RequestBody UrlCreateRequest urlCreateRequest) {
		requestLimit.checkAvailability();

		UrlCreateResponse urlCreateResponse = urlService.saveUrl(urlCreateRequest);

		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{key}")
			.buildAndExpand(urlCreateResponse.urlKey())
			.toUri();

		return ResponseEntity.created(uri)
			.body(urlCreateResponse);
	}

	@GetMapping("/counts")
	public ResponseEntity<Integer> urlCount(@RequestParam String url) {
		Integer count = urlService.countUrl(url);

		return ResponseEntity.ok(count);
	}
}
