package com.programmers.urlshortener.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// Common
	INTERNAL_SERVER_ERROR("C_001", "Internal Server Error"),

	// Url
	URL_NOT_FOUND("U_001", "URL을 찾을 수 없습니다."),
	;

	private final String code;
	private final String message;
}
