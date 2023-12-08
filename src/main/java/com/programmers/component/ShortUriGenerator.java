package com.programmers.component;

import com.programmers.dto.ShortUriGenerateRequestDto;

public interface ShortUriGenerator {
    String generate(ShortUriGenerateRequestDto requestDto);
}
