package com.spring.shorturl.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PostDto {
    public record SaveRequest(
            @NotEmpty @Size(max = 100)
            String title
    ) {
    }

    public record Response(
            Long id,
            String title
    ) {
    }
}
