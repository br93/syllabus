package com.syllabus.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.syllabus.exception.CacheException;
import com.syllabus.exception.CourseNotFoundException;
import com.syllabus.exception.CustomCallNotPermittedException;
import com.syllabus.exception.ProgramNotFoundException;
import com.syllabus.exception.StudentNotFoundException;
import com.syllabus.exception.UniversityInfoInvalidException;
import com.syllabus.exception.UserUnauthorizedException;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleUserNotFoundException(StudentNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleCourseNotFoundException(CourseNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UniversityInfoInvalidException.class)
    public ResponseEntity<Map<String, List<String>>> handleUniversityInfoValidException(
            UniversityInfoInvalidException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProgramNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleProgramNotFoundException(ProgramNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<Map<String, List<String>>> handleUserUnauthorizedException(UserUnauthorizedException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<Map<String, List<String>>> handleCallNotPermittedException(CallNotPermittedException ex) {
        var message = ex.getMessage().isEmpty() ? "service unavailable" : ex.getMessage();
        List<String> errors = Collections.singletonList(message);
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(CustomCallNotPermittedException.class)
    public ResponseEntity<Map<String, List<String>>> handleCustomCallNotPermittedException(
            CustomCallNotPermittedException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(CacheException.class)
    public ResponseEntity<Map<String, List<String>>> handleCacheException(CacheException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}
