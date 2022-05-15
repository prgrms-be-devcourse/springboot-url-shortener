package com.example.springbootboardjpa.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortUrlDto {
    private String shortId;
    private String url;
    private Long count;
    private LocalDateTime createdAt;
}
