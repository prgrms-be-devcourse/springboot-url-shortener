package prgrms.project.shorturl.domain;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prgrms.project.shorturl.dto.ShortUrlCreateRequest;
import prgrms.project.shorturl.dto.ShortUrlRedirectResponse;
import prgrms.project.shorturl.dto.ShortUrlRequest;
import prgrms.project.shorturl.dto.ShortUrlResponse;
import prgrms.project.shorturl.domain.ShortUrlService;

@RestController
@RequestMapping("/api/v1/short-urls")
public class ShortUrlRestController {

	private final ShortUrlService shortUrlService;

	public ShortUrlRestController(ShortUrlService shortUrlService) {
		this.shortUrlService = shortUrlService;
	}

	@PostMapping
	public ResponseEntity<ShortUrlResponse> createShortUrl(
		@RequestBody @Validated ShortUrlCreateRequest createRequest) {
		return ResponseEntity.status(CREATED).body(shortUrlService.createShortUrl(createRequest));
	}

	@PostMapping("/short-url")
	public ResponseEntity<ShortUrlRedirectResponse> requestToShortUrl(
		@RequestBody @Validated ShortUrlRequest shortUrlRequest) {
		var originUrl = shortUrlService.increaseRequestCount(shortUrlRequest.shortUrl());

		return ResponseEntity.ok(originUrl);
	}
}
