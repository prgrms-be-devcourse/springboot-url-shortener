package kdt.shorturl.url.dto;

import jakarta.validation.constraints.NotBlank;
import kdt.shorturl.grobal.validator.UrlValid;
import kdt.shorturl.url.domain.Algorithm;
import kdt.shorturl.url.domain.Url;

public record CreateShortUrlRequest(
        @NotBlank
        @UrlValid
        String originUrl,

        @NotBlank
        Algorithm algorithm
) {
    private static final String HTTPS_PROTOCOL = "https://";
    private static final String HTTP_PROTOCOL = "http://";

    public Url toEntity() {
        return Url.builder()
                .originUrl(removeProtocol(originUrl))
                .algorithm(algorithm)
                .build();
    }

    private String removeProtocol(String url) {
        if (url.startsWith(HTTPS_PROTOCOL)) {
            return url.substring(HTTPS_PROTOCOL.length());
        } else if (url.startsWith(HTTP_PROTOCOL)) {
            return url.substring(HTTP_PROTOCOL.length());
        }
        return url;
    }
}
