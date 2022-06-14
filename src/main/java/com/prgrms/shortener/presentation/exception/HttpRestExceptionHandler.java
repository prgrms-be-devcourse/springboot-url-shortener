package com.prgrms.shortener.presentation.exception;

import com.prgrms.shortener.presentation.HttpRestController;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {HttpRestController.class})
@Slf4j
public class HttpRestExceptionHandler {

  @ExceptionHandler({InvalidUrlRequestException.class})
  public SimpleMessagePayload invalidUrlRequestHandler(InvalidUrlRequestException exception, HttpServletResponse response) {
    log.warn(exception.getClass().getSimpleName(), exception);
    String message = exception.getMessage();
    response.setStatus(400);
    return new SimpleMessagePayload(message);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public SimpleMessagePayload illegalArgumentExceptionHandler(IllegalArgumentException exception, HttpServletResponse response) {
    log.warn(exception.getClass().getSimpleName(), exception);
    response.setStatus(400);
    return new SimpleMessagePayload(exception.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public SimpleMessagePayload exceptionHandler(Exception exception, HttpServletResponse response) {
    log.error(exception.getClass().getSimpleName(), exception);
    response.setStatus(500);
    return new SimpleMessagePayload("Internal Server Error");
  }

}
