package net.dmly.license.controller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleGeneralException(Exception ex, WebRequest request) {
        Map<String, Object> result = Map.of(
                "timestamp", LocalDateTime.now(),
                "exception message", Objects.toString(ex.getMessage()),
                "exception cause", Objects.toString(ex.getCause()),
                "stacktracke", ExceptionUtils.getStackTrace(ex)
        );

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
