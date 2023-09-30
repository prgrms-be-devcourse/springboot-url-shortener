package com.urlmaker.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public String notFoundException(){
        return "error/error404";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String validationException(){
        return "error/error500";
    }
}
