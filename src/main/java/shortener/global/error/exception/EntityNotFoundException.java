package shortener.global.error.exception;

import shortener.global.error.ErrorCode;

public class EntityNotFoundException extends RuntimeException {
	ErrorCode errorCode;

	public EntityNotFoundException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
