package com.seungwon.springbooturlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		return ResponseEntity.badRequest()
			.body(exception.getFieldError().getDefaultMessage());
	}

	@ExceptionHandler(NotFoundException.class)
	public View notFoundException(NotFoundException exception) {
		return new InternalResourceView("error");
	}

	@ExceptionHandler(InvalidStrategyException.class)
	public ResponseEntity<Object> invalidStrategyException(InvalidStrategyException exception) {
		return ResponseEntity.badRequest()
			.body("지원하지 않는 단축 방식입니다.");
	}

	@ExceptionHandler(InvalidUrlException.class)
	public ResponseEntity<Object> invalidUrlException(InvalidUrlException exception) {
		return ResponseEntity.badRequest()
			.body("유효하지 않은 url 입니다");
	}

	@ExceptionHandler(ExcessiveRequestException.class)
	public ResponseEntity<Object> excessiveRequestException(ExcessiveRequestException exception) {
		return ResponseEntity
			.status(HttpStatus.TOO_MANY_REQUESTS)
			.body("너무 많은 요청을 시도했습니다. 잠시 후에 다시 시도 바랍니다.");
	}

}
