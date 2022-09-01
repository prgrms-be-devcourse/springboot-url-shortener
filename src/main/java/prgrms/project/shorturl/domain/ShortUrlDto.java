package prgrms.project.shorturl.domain;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

public record ShortUrlDto() {

	public record CreateDto(
		@URL
		@NotBlank
		String originUrl,
		@NotBlank
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
