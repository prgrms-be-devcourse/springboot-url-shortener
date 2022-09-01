package prgrms.project.shorturl.domain;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import prgrms.project.shorturl.domain.ShortUrlDto.CreateDto;
import prgrms.project.shorturl.domain.ShortUrlDto.ResponseDto;
import prgrms.project.shorturl.global.exception.ErrorResponse;

@RestController
@RequestMapping("/api/v1/short-urls")
public class ShortUrlRestController {

	private final ShortUrlService shortUrlService;

	public ShortUrlRestController(ShortUrlService shortUrlService) {
		this.shortUrlService = shortUrlService;
	}

	@Operation(summary = "생성 API", description = "ShortUrl 생성 API 입니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "생성 성공",
			content = @Content(
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(implementation = ResponseDto.class))),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청",
			content = @Content(
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(implementation = ErrorResponse.class))),
	})
	@PostMapping
	public ResponseEntity<ResponseDto> createShortUrl(@Valid @RequestBody CreateDto createDto) {
		return ResponseEntity.ok(shortUrlService.createShortUrl(createDto));
	}

	@Operation(summary = "조회 API", description = "ShortUrl 조회 API 입니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "조회 성공",
			content = @Content(
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(implementation = ResponseDto.class))),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청",
			content = @Content(
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(implementation = ErrorResponse.class))),
	})
	@GetMapping("/{shortUrlId}")
	public ResponseEntity<ResponseDto> getShortUrl(
		@Parameter(description = "ShortUrl 아이디", required = true) @PathVariable Long shortUrlId
	) {
		return ResponseEntity.ok(shortUrlService.getShortUrl(shortUrlId));
	}

	@Operation(summary = "리다이렉트 API", description = "OriginUrl 리다이렉트 요청 API 입니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "리다이렉트 성공",
			content = @Content(
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(implementation = ResponseDto.class))),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청",
			content = @Content(
				mediaType = MediaType.APPLICATION_JSON_VALUE,
				schema = @Schema(implementation = ErrorResponse.class))),
	})
	@PutMapping("/{shortUrlId}")
	public ResponseEntity<ResponseDto> redirectToOriginUrl(
		@Parameter(description = "ShortUrl 아이디", required = true) @PathVariable Long shortUrlId
	) {
		return ResponseEntity.ok(shortUrlService.redirectToOriginUrl(shortUrlId));
	}
}
