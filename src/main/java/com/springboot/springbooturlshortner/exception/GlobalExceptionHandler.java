package com.springboot.springbooturlshortner.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlException.class)
    public String handleUrlException(UrlException e, Model model) {
        log.warn(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "errorPage";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        log.warn(e.getMessage());
        model.addAttribute("errorMessage", "서버 에러가 발생했습니다.");
        return "errorPage";
    }
}
