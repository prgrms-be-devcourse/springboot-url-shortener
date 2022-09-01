package prgrms.project.shorturl.global.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.validation.BindingResult;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponse(
	@Schema(description = "에러 코드")
	String errorCode,
	@Schema(description = "에러 메시지")
	String message,
	@Schema(description = "필드 에러 리스트")
	List<CustomFieldError> errors
) {

	public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode.code(), errorCode.message(), Collections.emptyList());
	}

	public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
		return new ErrorResponse(errorCode.code(), errorCode.message(), CustomFieldError.from(bindingResult));
	}
}
