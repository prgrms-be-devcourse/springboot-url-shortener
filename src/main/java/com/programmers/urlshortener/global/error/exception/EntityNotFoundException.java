package com.programmers.urlshortener.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.programmers.urlshortener.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class EntityNotFoundException extends RuntimeException {

	private final ErrorCode errorCode;
}
