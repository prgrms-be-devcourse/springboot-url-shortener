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

	@PostMapping("/v1/util/short-url")
	public ResponseEntity<ShortUrlCreateResponse> createShortUrl(String originalUrl) {
		log.info("Receive request to create originalUrl({}) to shortURL...", originalUrl);
		ShortUrlCreateResponse response = shortenerService.createShortUrl(originalUrl);
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

	/**
	 * <h2>clicks에 사용한 캐시 전략과 Redis <-> MySQL 데이터 일치전략 소개</h2>
	 * Redis는 서버가 다운될 것에 대비해 어느 정도의 데이터를 백업해둡니다.<br>
	 * <br>
	 * 하지만 완벽한 백업이 아니기 때문에 Redis에서 사용하는 캐싱 데이터는<br>
	 * 다음의 조건을 어느 정도 만족하는 데이터에 사용하면 좋다고 생각합니다.<br>
	 * 1) 캐싱 했을 때의 성능(속도) 향상, 2) 손실되어도 괜찮은 데이터<br>
	 * <br>
	 * 현재 이 서버에서는 Redis를 두 가지 용도로 사용 중 입니다.<br>
	 * 1) 리다이렉션으로 보낼 원본 url을 빠르게 찾기 위해<br>
	 * 2) 인코딩된 url의 총 click 수를 빠르게 저장하고 조회하기 위해<br>
	 * <br>
	 */
	// @GetMapping("/{encodedUrl}/clicks")
	// public ResponseEntity<ClicksResponse> getClicks(@PathVariable String encodedUrl) {
	// 	ClicksResponse response = shortenerService.findClicks(encodedUrl);
	// }

	private HttpHeaders createRedirectionHeader(String originalUrl) {
		log.info("Create redirection Http headers...");
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.HOST, "http://yosongsong.shortener.co.kr");
		headers.add(HttpHeaders.SERVER, "Tomcat");
		headers.add(HttpHeaders.LOCATION, originalUrl);
		headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0");
		log.info("Success to create headers");

		return headers;
	}
}
