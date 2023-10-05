package com.example.kdtspringbooturlshortener.global.error.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.validation.FieldError;

import java.util.List;

@Builder
public record ErrorResponse(
        String message,
        @JsonInclude(JsonInclude.Include.NON_EMPTY) List<FieldErrorStatus> errorStatuses
) {

    public record FieldErrorStatus(
            String field,
            String value
    ) {
        public static FieldErrorStatus of(FieldError fieldError) {
            return new FieldErrorStatus(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
