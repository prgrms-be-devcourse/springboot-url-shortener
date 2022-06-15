package com.pppp0722.springbooturlshortener.controller.url;

import com.pppp0722.springbooturlshortener.exception.IllegalUrlArgumentException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.pppp0722.springbooturlshortener.controller.view")
public class UrlViewControllerAdvice {

    @ExceptionHandler(IllegalUrlArgumentException.class)
    public String handleIllegalUrlArgumentException(IllegalUrlArgumentException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error/401";
    }
}
