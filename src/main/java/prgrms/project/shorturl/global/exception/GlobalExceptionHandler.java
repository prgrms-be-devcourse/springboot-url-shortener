package prgrms.project.shorturl.global.exception;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "prgrms.project.shorturl.domain")
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException ex,
		BindingResult bindingResult
	) {
		log.info("Got MethodArgumentNotValidException: {}", ex.getMessage(), ex);

		return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCode.INVALID_METHOD_ARGUMENT, bindingResult));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
		log.info("Got EntityNotFoundException: {}", ex.getMessage(), ex);

		return ResponseEntity.badRequest().body(ErrorResponse.of(ErrorCode.ENTITY_NOT_FOUND));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("Got Exception: {}", ex.getMessage(), ex);

		return ResponseEntity.internalServerError().body(ErrorResponse.of(ErrorCode.UNKNOWN_SERVER_ERROR));
	}
}
