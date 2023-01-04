package org.prgrms.url.shortner.springbooturlshortner.global.error.exception;

public class AlgorithmException extends RuntimeException {

	private static final String message = "알고리즘을 수행하는데 실패하였습니다.";

	public AlgorithmException() {
		super(message);
	}
}

