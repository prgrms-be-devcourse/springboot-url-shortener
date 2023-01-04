package org.prgrms.url.shortner.springbooturlshortner.global.error;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.prgrms.url.shortner.springbooturlshortner.global.error.exception.AlgorithmException;
import org.prgrms.url.shortner.springbooturlshortner.global.error.exception.EntityNotFoundException;
import org.prgrms.url.shortner.springbooturlshortner.global.error.exception.MaxAttemptException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AlgorithmException.class)
	public String algorithmException(AlgorithmException e, Model model) {
		log.info("error!={}", e.getClass());

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(e.getMessage())
			.statusCode(SERVICE_UNAVAILABLE.value())
			.build();

		model.addAttribute("errorResponse", errorResponse);
		return "error";

	}

	@ExceptionHandler(EntityNotFoundException.class)
	public String entityNotFoundException(EntityNotFoundException e, Model model) {
		log.info("error!={}", e.getClass());

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(e.getMessage())
			.statusCode(BAD_REQUEST.value())
			.build();

		model.addAttribute("errorResponse", errorResponse);
		return "error";
	}

	@ExceptionHandler(MaxAttemptException.class)
	public String maxAttemptException(MaxAttemptException e, Model model) {
		log.info("error!={}", e.getClass());

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(e.getMessage())
			.statusCode(INTERNAL_SERVER_ERROR.value())
			.build();

		model.addAttribute("errorResponse", errorResponse);
		return "error";
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String methodArgumentNotValidException(MethodArgumentNotValidException e, Model model) {
		log.info("error!={}", e.getClass());

		BindingResult bindingResult = e.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(fieldErrors.get(0).getDefaultMessage())
			.statusCode(BAD_REQUEST.value())
			.build();

		model.addAttribute("errorResponse", errorResponse);
		return "error";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public String illegalArgumentException(IllegalArgumentException e, Model model) {
		log.info("error!={}", e.getClass());

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(e.getMessage())
			.statusCode(BAD_REQUEST.value())
			.build();

		model.addAttribute("errorResponse", errorResponse);
		return "error";
	}

	@ExceptionHandler(BindException.class)
	public String bindException(BindException e, Model model) {
		log.info("error!={}", e.getClass());

		BindingResult bindingResult = e.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		ErrorResponse errorResponse = ErrorResponse.builder()
			.message(fieldErrors.get(0).getDefaultMessage())
			.statusCode(BAD_REQUEST.value())
			.build();

		model.addAttribute("errorResponse", errorResponse);
		return "error";
	}

}
