package com.dev.shortenerurl.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

	@ExceptionHandler(CommonException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleCommonException(CommonException e, Model model) {
		log.warn("custom exception : ", e);

		model.addAttribute("errorMessage", e.getMessage());
		return "/error/error";
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleCommonException(RuntimeException e, Model model) {
		log.error("server error : ", e);

		model.addAttribute("errorMessage", "서버 내부 오류입니다. 다시 시도해주세요.");
		return "/error/error";
	}
}
