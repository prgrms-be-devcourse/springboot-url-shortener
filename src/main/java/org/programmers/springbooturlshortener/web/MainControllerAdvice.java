package org.programmers.springbooturlshortener.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class MainControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("클라이언트에게 잘못된 인자 값을 전달받음, 에러 정보:", e);
        return "/errors/4xx";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public String handleOtherException(Exception e) {
        log.error("식별되지 않은 오류에 대해 에러 페이지 표시함, 에러 정보:", e);
        return "/errors/5xx";
    }
}
