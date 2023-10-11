package com.programmers.springbooturlshortener.global.domain.exception;

import lombok.Getter;

@Getter
public class EntityNotFound extends UrlApplicationException {

	private static final String MESSAGE = "저장된 정보를 찾을 수 없습니다.";

	public EntityNotFound() {
		super(MESSAGE);
	}
	@Override
	public int getStatusCode() {
		return 400;
	}
}
