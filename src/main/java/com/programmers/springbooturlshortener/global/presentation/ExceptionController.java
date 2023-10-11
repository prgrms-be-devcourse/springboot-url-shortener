package com.programmers.springbooturlshortener.global.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.programmers.springbooturlshortener.global.domain.exception.UrlApplicationException;
import com.programmers.springbooturlshortener.global.presentation.response.ExceptionResponse;

@RestControllerAdvice
public class ExceptionController {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionResponse invalidRequestHandler(MethodArgumentNotValidException e) {
		ExceptionResponse response = ExceptionResponse.builder()
			.code("400")
			.message("잘못된 요청입니다.")
			.build();

		for (FieldError fieldError : e.getFieldErrors()) {
			response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
		}

		return response;
	}

	@ExceptionHandler(UrlApplicationException.class)
	public ResponseEntity<ExceptionResponse> urlApplicationException(UrlApplicationException e) {
		int statusCode = e.getStatusCode();

		ExceptionResponse body = ExceptionResponse.builder()
			.code(String.valueOf(statusCode))
			.message(e.getMessage())
			.validation(e.getValidation())
			.build();

		return ResponseEntity.status(statusCode).body(body);
	}
}
