package com.prgrms.shortener.presentation.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class InvalidUrlRequestException extends RuntimeException {

  public InvalidUrlRequestException(BindingResult bindingResult) {
    super(bindingResult.getAllErrors().get(0).getDefaultMessage());

  }
}
