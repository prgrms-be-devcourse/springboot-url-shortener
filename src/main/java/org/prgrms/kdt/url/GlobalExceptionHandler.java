package org.prgrms.kdt.url;

import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  protected String handleNotFoundException(NoSuchElementException ex) {
    log.error("페이지를 찾을 수 없습니다. : {}", ex.getMessage());
    return "404";
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  protected String handleServerException(Exception ex) {
    log.error("예상하지 못한 에러가 발생했습니다 : {}", ex.getMessage());
    return "500";
  }

}