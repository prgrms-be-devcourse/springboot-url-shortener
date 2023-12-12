package com.programmers.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UriControllerAdvice {

    @ExceptionHandler(Exception.class)
    public String handle(Exception e) {
        return "error";
    }
}
