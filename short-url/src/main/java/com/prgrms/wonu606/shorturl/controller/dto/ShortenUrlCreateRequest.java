package com.prgrms.wonu606.shorturl.controller.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record ShortenUrlCreateRequest(
        @NotNull
        @URL
        String originalUrl) {

}
