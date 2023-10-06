package com.progms.shorturl.global.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.validation.FieldError;

import java.util.List;

@Builder
public record ErrorResponse(
        String message,
        @JsonInclude(JsonInclude.Include.NON_EMPTY) List<ErrorStatus> errors
) {

    public record ErrorStatus(
            String field,
            String message
    ) {

        public static ErrorStatus of(final FieldError fieldError) {
            return new ErrorStatus(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
