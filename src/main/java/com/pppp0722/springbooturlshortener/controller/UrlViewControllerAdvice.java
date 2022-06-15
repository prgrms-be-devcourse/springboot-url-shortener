package com.pppp0722.springbooturlshortener.controller;

import com.pppp0722.springbooturlshortener.exception.UrlIllegalArgumentException;
import com.pppp0722.springbooturlshortener.exception.UrlNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.pppp0722.springbooturlshortener.controller")
public class UrlViewControllerAdvice {

    @ExceptionHandler(UrlIllegalArgumentException.class)
    public String handleIllegalUrlArgumentException(UrlIllegalArgumentException e, Model model) {
        model.addAttribute("message", e.getMessage());

        return "error/400";
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public String handleUrlNotFoundException(UrlNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage());

        return "error/404";
    }
}
