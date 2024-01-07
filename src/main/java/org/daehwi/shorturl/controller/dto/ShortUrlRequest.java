package org.daehwi.shorturl.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ShortUrlRequest {
    private static final EncodeType DEFAULT_ENCODE_TYPE = EncodeType.BASE62;

    @NotBlank(message = "url must not be blank")
    private String url;

    private EncodeType encodeType;

    public ShortUrlRequest(String url, EncodeType encodeType) {
        this.url = url;
        this.encodeType = encodeType == null ? DEFAULT_ENCODE_TYPE : encodeType;
    }
}
