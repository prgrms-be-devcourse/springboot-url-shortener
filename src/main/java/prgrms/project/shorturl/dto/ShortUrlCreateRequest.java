package prgrms.project.shorturl.dto;

import javax.validation.constraints.NotBlank;

import prgrms.project.shorturl.global.validation.UrlChecker;

public record ShortUrlCreateRequest(
	@UrlChecker(min = 4, max = 500)
	String originUrl,

	@NotBlank
	String algorithm
) {
}
