package com.pgms.shorturlcoredomain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    private final String code;
    private final String message;
}
