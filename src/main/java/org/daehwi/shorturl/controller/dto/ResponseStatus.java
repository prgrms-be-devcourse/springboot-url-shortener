package org.daehwi.shorturl.controller.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ResponseStatus {
	OK(HttpStatus.OK, "2000", "요청이 정상적으로 처리되었습니다."),
	TEMPORARY_REDIRECT(HttpStatus.TEMPORARY_REDIRECT, "3020", "요청하신 페이지로 이동합니다."),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "4000", "잘못된 요청입니다."),
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "4001", "잘못된 요청 본문입니다. 알맞은 요청 본문을 입력해주세요."),
	INVALID_URL(HttpStatus.BAD_REQUEST, "4002", "잘못된 URL 요청입니다."),
	PROTOCOL_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "4003", "지원하지 않는 프로토콜입니다. HTTP 또는 HTTPS 프로토콜을 사용해주세요."),
	PROTOCOL_NOT_FOUND(HttpStatus.BAD_REQUEST, "4004", "프로토콜을 찾을 수 없습니다."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "4040", "요청하신 페이지를 찾을 수 없습니다."),
	SHORT_URL_NOT_FOUND(HttpStatus.BAD_REQUEST, "4041", "요청하신 Short URL을 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000", "서버에 오류가 발생하였습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	ResponseStatus(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}
}
