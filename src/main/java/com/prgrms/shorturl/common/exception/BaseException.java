package com.prgrms.shorturl.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BaseException extends RuntimeException{

    private final ErrorCode errorCode;
}
