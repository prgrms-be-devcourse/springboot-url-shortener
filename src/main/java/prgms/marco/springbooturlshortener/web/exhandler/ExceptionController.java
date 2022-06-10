package prgms.marco.springbooturlshortener.web.exhandler;

import java.util.Map;
import javax.xml.bind.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prgms.marco.springbooturlshortener.exception.ClientException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> serverExceptionHandler(Exception exception) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("message", exception.getMessage()));
    }

    @ExceptionHandler(value = {ClientException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> clientExceptionHandler(Exception exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Map.of("message", exception.getMessage()));
    }
}
