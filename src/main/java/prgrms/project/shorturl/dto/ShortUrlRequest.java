package prgrms.project.shorturl.dto;

import javax.validation.constraints.Size;

public record ShortUrlRequest(
    @Size(max = 100)
    String shortUrl
) {
}
