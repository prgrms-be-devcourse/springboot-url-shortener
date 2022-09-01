package prgrms.project.shorturl.domain;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import prgrms.project.shorturl.domain.ShortUrlDto.CreateDto;
import prgrms.project.shorturl.domain.ShortUrlDto.ResponseDto;

@RestController
@RequestMapping("/api/v1/short-urls")
public class ShortUrlRestController {

	private final ShortUrlService shortUrlService;

	public ShortUrlRestController(ShortUrlService shortUrlService) {
		this.shortUrlService = shortUrlService;
	}

	@PostMapping
	public ResponseEntity<ResponseDto> createShortUrl(@Valid @RequestBody CreateDto createDto) {
		return ResponseEntity.ok(shortUrlService.createShortUrl(createDto));
	}
}
