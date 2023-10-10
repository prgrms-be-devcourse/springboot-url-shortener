package com.prgrms.shorturl.global.error.handler;

import com.prgrms.shorturl.global.error.response.ErrorResponse;
import com.prgrms.shorturl.url.exception.ExistedOriginUrlException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.prgrms.shorturl.global.error.ErrorCode.*;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * [Custom Exception] Origin Url이 이미 존재하는 경우
     */
    @ExceptionHandler(ExistedOriginUrlException.class)
    protected ResponseEntity<ErrorResponse> handleExistedOriginUrlException(ExistedOriginUrlException e) {
        log.warn("Handle ExistedOriginUrlException", e);
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode(), e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }


    /**
     * [Exception] 객체 혹은 파라미터의 데이터 값이 유효하지 않은 경우
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("Handle MethodArgumentNotValidException", e.getMessage());
        final ErrorResponse response = ErrorResponse.of(INVALID_METHOD_ERROR, e.getBindingResult());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * [Exception] 클라이언트에서 request의 '파라미터로' 데이터가 넘어오지 않았을 경우
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> handleMissingRequestHeaderExceptionException(
            MissingServletRequestParameterException ex) {
        log.warn("Handle MissingServletRequestParameterException", ex);
        final ErrorResponse response = ErrorResponse.of(REQUEST_PARAM_MISSING_ERROR, ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * [Exception] enum type 일치하지 않아 binding 못할 경우
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.warn("Handle MethodArgumentTypeMismatchException", e);
        final ErrorResponse response = ErrorResponse.of(INVALID_INPUT_VALUE_ERROR, e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * [Exception] 서버에 정의되지 않은 모든 예외
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception e) {
        log.error("Handle Exception :", e);
        final ErrorResponse response = ErrorResponse.of(INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

}
