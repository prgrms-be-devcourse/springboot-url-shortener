package org.prgrms.url.shortner.springbooturlshortner.global.error.exception;

public class MaxAttemptException extends RuntimeException {

	private static final String message = "알고리즘 시도 횟수를 초과하였습니다.";

	public MaxAttemptException() {
		super(message);
	}
}
