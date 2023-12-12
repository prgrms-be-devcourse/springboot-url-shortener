package org.daehwi.shorturl.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private final String code;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> of(ResponseStatus status) {
        return new ApiResponse<>(status.getCode(), status.getCode(), null);
    }

    public static <T> ApiResponse<T> of(ResponseStatus status, T data) {
        return new ApiResponse<>(status.getCode(), status.getCode(), data);
    }
}
