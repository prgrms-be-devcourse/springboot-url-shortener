package com.pgms.shorturlcoredomain.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
    private final int code;
    private final T data;
}
