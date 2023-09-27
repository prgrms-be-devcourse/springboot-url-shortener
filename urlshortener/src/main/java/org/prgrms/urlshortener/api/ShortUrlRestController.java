package org.prgrms.urlshortener.api;

import org.prgrms.urlshortener.application.ShortUrlService;
import org.prgrms.urlshortener.dto.request.ShortUrlCreateRequest;
import org.prgrms.urlshortener.dto.response.ShortUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/short_urls")
@RequiredArgsConstructor
@RestController
public class ShortUrlRestController {

	private final ShortUrlService shortUrlService;

	@PostMapping
	public ResponseEntity<ShortUrlResponse> encodeUrl(@RequestBody @Valid ShortUrlCreateRequest request) {
		ShortUrlResponse response = shortUrlService.encodeUrl(request);

		return ResponseEntity.ok(response);
	}

}
