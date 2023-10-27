package com.syllabus.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

@RestControllerAdvice
public class CoreExceptionHandler {

    @ExceptionHandler(CallNotPermittedException.class)
     public ResponseEntity<Map<String, List<String>>> handleCallNotPermittedException(CallNotPermittedException ex) {
        var message = ex.getMessage().isEmpty() ? "service unavailable" : ex.getMessage();
        List<String> errors = Collections.singletonList(message);
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(CustomCallNotPermittedException.class)
     public ResponseEntity<Map<String, List<String>>> handleCustomCallNotPermittedException(CustomCallNotPermittedException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

     private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
    
}
