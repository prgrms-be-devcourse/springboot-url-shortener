package com.prgrms.shortener.presentation.exception;

import com.prgrms.shortener.domain.exception.ShortenedUrlNotFoundException;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class HttpExceptionHandler {

  @ExceptionHandler(ShortenedUrlNotFoundException.class)
  public String shortenedUrlNotFoundExceptionHandler(ShortenedUrlNotFoundException exception, HttpServletResponse response) {
    log.warn(exception.getClass().getSimpleName(), exception);
    response.setStatus(404);
    return "failure";
  }

  @ExceptionHandler({InvalidUrlRequestException.class})
  @ResponseBody
  public SimpleMessagePayload invalidUrlRequestHandler(InvalidUrlRequestException exception, HttpServletResponse response) {
    log.warn(exception.getClass().getSimpleName(), exception);
    String message = exception.getMessage();
    response.setStatus(400);
    return new SimpleMessagePayload(message);
  }

  @ExceptionHandler({IllegalArgumentException.class})
  @ResponseBody
  public SimpleMessagePayload illegalArgumentExceptionHandler(IllegalArgumentException exception, HttpServletResponse response) {
    log.warn(exception.getClass().getSimpleName(), exception);
    response.setStatus(400);
    return new SimpleMessagePayload(exception.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public SimpleMessagePayload exceptionHandler(Exception exception, HttpServletResponse response) {
    log.error(exception.getClass().getSimpleName(), exception);
    response.setStatus(500);
    return new SimpleMessagePayload("Internal Server Error");
  }

}
