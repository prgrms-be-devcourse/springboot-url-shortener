package prgrms.project.shorturl.domain;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

public record ShortUrlDto() {

	public record CreateDto(
		@URL
		String originUrl,
		@NotBlank(message = "ShortUrl 생성 방식을 설정해주세요.")
		String method
	) {
	}

	public record ResponseDto(
		Long shortUrlId,
		String originUrl,
		String shortenUrl,
		int numberOfRequests
	) {
	}
}
