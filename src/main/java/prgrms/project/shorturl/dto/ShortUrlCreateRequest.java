package prgrms.project.shorturl.dto;

import javax.validation.constraints.NotBlank;

public record ShortUrlCreateRequest(
	String originUrl,

	@NotBlank
	String method
) {
}
