package com.programmers.urlshortener.error;

import com.programmers.urlshortener.error.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

	private final String error;

	private final String code;

	private final String message;

	public static ErrorResponse from(ErrorCode errorCode) {
		return new ErrorResponse(
			errorCode.name(),
			errorCode.getCode(),
			errorCode.getMessage()
		);
	}
}
