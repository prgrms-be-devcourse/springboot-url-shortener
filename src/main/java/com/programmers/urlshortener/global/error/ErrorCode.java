package com.programmers.urlshortener.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	INVALID_URL_VALUE(HttpStatus.BAD_REQUEST, "입력하신 URL이 유효하지 않습니다."),
	URL_NOT_FOUND(HttpStatus.NOT_FOUND, "URL을 찾을 수 없습니다."),
	ENCODER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 Encoder 입니다.");

	private final HttpStatus code;
	private final String message;
}
