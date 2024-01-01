package org.prgms.springbooturlshortener.domain.shorturl.controller;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbooturlshortener.domain.shorturl.exception.UrlException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    private static final int HTTP_500_ERROR_BOUND = 400;
    @ExceptionHandler(UrlException.class)
    public String showUrlErrorPage(UrlException ex) {
        if (ex.getUrlErrorCode() > HTTP_500_ERROR_BOUND) {
            log.warn("서버에서의 에러: {}", ex.getMessage());

            return "error/500";
        }

        log.info("클라이언트에서 잘못된 입력. {}", ex.getMessage());

        return "error/400";
    }

    @ExceptionHandler(Exception.class)
    public String showDefaultError(Exception ex) {
        log.error("예상하지 못한 에러 발생: {}", ex.getMessage());

        return "error/500";
    }
}
