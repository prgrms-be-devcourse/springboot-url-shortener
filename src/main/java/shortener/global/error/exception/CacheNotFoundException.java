package shortener.global.error.exception;

import shortener.global.error.ErrorCode;

public class CacheNotFoundException extends RuntimeException {

	ErrorCode errorCode;

	public CacheNotFoundException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
