package prgrms.project.shorturl.global.exception;

import static java.text.MessageFormat.*;
import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static prgrms.project.shorturl.global.exception.ErrorCode.*;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "prgrms.project.shorturl.controller")
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	private String cause;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		var fieldError = ex.getFieldError();
		cause = format("필드에 맞지 않는 입력값을 입력하였습니다. (필드: {0}, 입력 값: {1})", requireNonNull(fieldError).getField(),
			fieldError.getRejectedValue());

		log.warn("Got MethodArgumentNotValidException: {}", cause, ex);

		return responseOf(INVALID_INPUT_VALUE, cause);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
		var supported = ex.getSupportedMediaTypes().stream().map(MimeType::toString).collect(joining());
		cause = format("{0} 는 지원하지 않는 미디어 타입 입니다. (지원 목록: {1})", ex.getContentType(), supported);

		log.warn("Got HttpMediaTypeNotSupportedException: {}", cause, ex);

		return responseOf(NOT_SUPPORTED_MEDIA_TYPE, cause);
	}

	@ExceptionHandler(TypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleTypeMismatch(TypeMismatchException ex) {
		cause = format("잘못된 타입이 입력되었습니다. (기대 타입: {0}, 입력 타입: {1})",
			requireNonNull(ex.getRequiredType()).getSimpleName(), ex.getValue());

		log.warn("Got TypeMismatchException: {}", cause, ex);

		return responseOf(TYPE_MISMATCH, cause);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
		log.warn("Got EntityNotFoundException: {}", ex.getMessage(), ex);

		return responseOf(ENTITY_NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	protected ResponseEntity<ErrorResponse> missingServletRequestParameterException(HttpServletRequest request,
		MissingServletRequestParameterException ex) {
		cause = format("필요한 파라미터가 존재하지 않습니다. (필요한 파라미터: {0}, 요청된 파라미터: {1})", ex.getParameterName(),
			request.getQueryString());

		log.warn("Got MissingServletRequestParameterException: {}", cause, ex);

		return responseOf(MISSING_QUERY_PARAMETER, cause);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleExceptionInternal(Exception ex) {
		log.error("Got unknown server error: {}", ex.getMessage(), ex);

		return responseOf(SERVER_ERROR, ex.getMessage());
	}

	private ResponseEntity<ErrorResponse> responseOf(ErrorCode errorCode, String cause) {
		return ResponseEntity
			.status(errorCode.getStatusCode())
			.body(
				new ErrorResponse(errorCode.getMessage(), cause)
			);
	}
}
