package shortener.global.error.exception;

import shortener.global.error.ErrorCode;

public class BusinessException extends RuntimeException {
	ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
