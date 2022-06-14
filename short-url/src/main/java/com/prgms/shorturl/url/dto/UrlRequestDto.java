package com.prgms.shorturl.url.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlRequestDto {

    @NotBlank
    @Pattern(regexp = "(https?:\\/\\/)?([\\w\\d-_]+)\\.([\\w\\d-_\\.]+)\\/?\\??([^#\\n\\r]*)?#?([^\\n\\r]*)")
    private String longUrl;
}
