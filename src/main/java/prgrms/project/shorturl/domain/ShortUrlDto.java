package prgrms.project.shorturl.domain;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShortUrlDto() {

	public record CreateDto(
		@Schema(description = "원본 Url", required = true)
		@URL
		@NotBlank
		String originUrl,

		@Schema(description = "ShortUrl 생성 방법", required = true)
		@NotBlank
		String method
	) {
	}

	public record ResponseDto(
		@Schema(description = "ShortUrl 아이디")
		Long shortUrlId,
		@Schema(description = "원본 Url")
		String originUrl,
		@Schema(description = "생성된 ShortUrl")
		String shortenUrl,
		@Schema(description = "원본 url 리다이렉트 요청 횟수")
		int numberOfRequests
	) {
	}
}
