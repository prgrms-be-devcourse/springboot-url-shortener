package com.prgrms.url_shortener.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
        Model model) {
        log.error("handleMethodArgumentNotValidException", exception);

        StringBuilder sb = new StringBuilder();
        for (FieldError e : exception.getBindingResult().getFieldErrors()) {
            sb.append(e.getDefaultMessage());
            sb.append(", ");
        }

        model.addAttribute("errorMessage", sb.toString());
        return "errorPage";
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleCustomException(final CustomException exception, Model model) {
        log.error("handleCustomException", exception);
        model.addAttribute("errorMessage", exception.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected String handleException(final Exception exception, Model model) {
        log.error("handleException", exception);
        model.addAttribute("errorMessage", "서버에 오류가 발생하였습니다.");
        return "errorPage";
    }
}
