package com.devcourse.shorturl.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class WebExceptionHandler {
    @ExceptionHandler(CommonException.class)
    public String handleWebException(CommonException e, Model model){
        log.error(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "errorPage";
    }
}
