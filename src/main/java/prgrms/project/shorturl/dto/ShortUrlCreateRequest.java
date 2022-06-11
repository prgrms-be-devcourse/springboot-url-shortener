package prgrms.project.shorturl.dto;


import prgrms.project.shorturl.global.validation.UrlChecker;

import javax.validation.constraints.NotBlank;

public record ShortUrlCreateRequest(
    @UrlChecker(min = 4, max = 500)
    String originUrl,

    @NotBlank
    String algorithm
) {
}
