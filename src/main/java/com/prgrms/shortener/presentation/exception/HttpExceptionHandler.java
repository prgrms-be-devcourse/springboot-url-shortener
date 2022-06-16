package com.prgrms.shortener.presentation.exception;

import com.prgrms.shortener.domain.exception.ShortenedUrlNotFoundException;
import com.prgrms.shortener.presentation.HttpController;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = HttpController.class)
@Slf4j
public class HttpExceptionHandler {

  @ExceptionHandler(ShortenedUrlNotFoundException.class)
  public String shortenedUrlNotFoundExceptionHandler(ShortenedUrlNotFoundException exception, HttpServletResponse response) {
    log.warn(exception.getClass().getSimpleName(), exception);
    response.setStatus(404);
    return "failure";
  }

  @ExceptionHandler(Exception.class)
  public String exceptionHandler(Exception exception, HttpServletResponse response) {
    log.error(exception.getClass().getSimpleName(), exception);
    response.setStatus(500);
    return "500";
  }
}
