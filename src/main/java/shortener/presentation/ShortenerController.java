package shortener.presentation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import shortener.application.ShortenerService;
import shortener.application.dto.response.ClicksResponse;
import shortener.application.dto.response.ShortUrlCreateResponse;

@Slf4j
@RestController
public class ShortenerController {

	private final ShortenerService shortenerService;

	public ShortenerController(ShortenerService shortenerService) {
		this.shortenerService = shortenerService;
	}

	@PostMapping("/v1/util/short-url/{algorithmId}")
	public ResponseEntity<ShortUrlCreateResponse> createShortUrl(
		String originalUrl,
		@PathVariable int algorithmId
	) {
		log.info("Receive request to create originalUrl({}) to shortURL...", originalUrl);
		ShortUrlCreateResponse response = shortenerService.saveNewShortUrl(originalUrl, algorithmId);
		log.info("Success to create shortURL : {}", response.shortUrl());

		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(response);
	}

	@GetMapping("/{encodedUrl}")
	public ResponseEntity<Void> getOriginalUrl(@PathVariable String encodedUrl) {
		String originalUrl = shortenerService.findOriginalUrl(encodedUrl);
		HttpHeaders headers = createRedirectionHeader(originalUrl);

		return ResponseEntity
			.status(HttpStatus.MOVED_PERMANENTLY)
			.headers(headers)
			.build();
	}

	@GetMapping("/{encodedUrl}/clicks")
	public ResponseEntity<ClicksResponse> getClicks(@PathVariable String encodedUrl) {
		log.info("Receive request to get clicks for encodedUrl({})...", encodedUrl);
		ClicksResponse response = shortenerService.findClicks(encodedUrl);
		log.info("Success to get clicks({}) for encodedUrl({})",
			response.clicks(), response.encodedUrl());

		return ResponseEntity.ok(response);
	}

	private HttpHeaders createRedirectionHeader(String originalUrl) {
		String httpAppendedOriginalUrl = appendHttpToUrlIfAbsent(originalUrl);

		log.info("Create redirection Http headers...");
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.HOST, "http://yosongsong.shortener.co.kr");
		headers.add(HttpHeaders.SERVER, "Tomcat");
		headers.add(HttpHeaders.LOCATION, httpAppendedOriginalUrl);
		headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0");
		log.info("Success to create headers");

		return headers;
	}

	private String appendHttpToUrlIfAbsent(String originalUrl) {
		log.info("Trying to append \"http://\" to url if absent...");
		boolean hasHttp = originalUrl.startsWith("http://");
		boolean hasHttps = originalUrl.startsWith("https://");

		if (hasHttp || hasHttps) {
			log.info("Already exist \"http://\" or \"https://\" => {}", originalUrl);

			return originalUrl;
		}

		String httpAppended = "http://" + originalUrl;
		log.info("append \"http://\" => {}", httpAppended);

		return httpAppended;
	}
}
