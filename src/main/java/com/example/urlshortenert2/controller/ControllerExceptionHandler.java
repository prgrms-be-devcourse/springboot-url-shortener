package com.example.urlshortenert2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> notFoundHandler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<Object> illegalUrlHandler() {
        return ResponseEntity.badRequest()
                .body("잘못된 URL 형식입니다.(http://, https:// 를 반드시 넣어 주어야 합니다.)");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> notConnectionUrlHandler(IllegalArgumentException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<Object> notPresentDomainUrlHandler() {
        return ResponseEntity.badRequest()
                .body("존재하지 않는 도메인의 URL 입니다.");
    }
}
