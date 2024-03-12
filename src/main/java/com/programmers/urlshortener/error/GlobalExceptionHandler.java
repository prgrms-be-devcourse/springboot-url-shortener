package com.programmers.urlshortener.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.programmers.urlshortener.error.exception.BaseException;
import com.programmers.urlshortener.error.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected String handleException(
		final Exception e,
		final Model model
	) {
		log.error("UnExpected Exception", e);
		final ErrorResponse response = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR);
		model.addAttribute("response", response);

		return "error";
	}

	@ExceptionHandler(BaseException.class)
	protected String handleBusinessException(
		final BaseException e,
		final Model model
	) {
		log.error("BaseException", e);
		final ErrorResponse response = ErrorResponse.from(e.getErrorCode());
		model.addAttribute("response", response);

		return "error";
	}
}
