package com.seungwon.springbooturlshortener.presentation;

import static org.springframework.http.HttpStatus.MOVED_PERMANENTLY;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.seungwon.springbooturlshortener.application.UrlService;
import com.seungwon.springbooturlshortener.application.dto.UrlCreateRequest;
import com.seungwon.springbooturlshortener.application.dto.UrlCreateResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UrlController {

	private final UrlService urlService;

	@PostMapping("/api/urls")
	public ResponseEntity<UrlCreateResponse> urlSave(@Validated @RequestBody UrlCreateRequest urlCreateRequest) {
		UrlCreateResponse urlCreateResponse = urlService.saveUrl(urlCreateRequest);

		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{key}")
			.buildAndExpand(urlCreateResponse.urlKey())
			.toUri();

		return ResponseEntity.created(uri)
			.body(urlCreateResponse);
	}

	@GetMapping("/{key}")
	public ResponseEntity<Object> urlLoad(@PathVariable String key) {
		String originalUrl = urlService.loadUrl(key);

		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.setLocation(URI.create(originalUrl));

		return new ResponseEntity<>(httpHeader, MOVED_PERMANENTLY);
	}

	@GetMapping("/api/counts")
	public ResponseEntity<Integer> urlCount(@RequestParam String url) {
		Integer count = urlService.countUrl(url);

		return ResponseEntity.ok(count);
	}
}
