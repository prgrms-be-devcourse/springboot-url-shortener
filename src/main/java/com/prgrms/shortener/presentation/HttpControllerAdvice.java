package com.prgrms.shortener.presentation;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HttpControllerAdvice {

  @ExceptionHandler(ShortenedUrlNotFoundException.class)
  public String shortenedUrlNotFoundExceptionHandler(HttpServletResponse response) {
    response.setStatus(404);
    return "failure";
  }

}
