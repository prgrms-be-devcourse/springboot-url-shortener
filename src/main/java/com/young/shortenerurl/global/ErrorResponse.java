package com.young.shortenerurl.global;

import java.time.LocalDateTime;

public record ErrorResponse(
        String detail,
        String instance,
        String time) {

    public static ErrorResponse of(
            String detail,
            String instance) {

        return new ErrorResponse(
                detail,
                instance,
                LocalDateTime.now().toString());
    }

}
