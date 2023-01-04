package org.prgrms.url.shortner.springbooturlshortner.global.error.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final String message = "조회를 실패하였습니다.";

	public EntityNotFoundException() {
		super(message);
	}
}
