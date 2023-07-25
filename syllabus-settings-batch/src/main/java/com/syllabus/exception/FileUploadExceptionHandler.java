package com.syllabus.exception;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class FileUploadExceptionHandler extends ResponseEntityExceptionHandler {

	private final Environment environment;

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Map<String, List<String>>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
		List<String> errors = Collections.singletonList(this.getMaxFileSizeMessage());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(CacheException.class)
	public ResponseEntity<Map<String, List<String>>> handleCacheException(CacheException ex) {
		List<String> errors = Collections.singletonList(ex.getMessage());
		return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
	}

	private String getMaxFileSizeMessage() {
		return "Max upload size of " + environment.getProperty("spring.servlet.multipart.max-file.size") + " exceeded";
	}

	private Map<String, List<String>> getErrorsMap(List<String> errors) {
		Map<String, List<String>> errorResponse = new HashMap<>();
		errorResponse.put("errors", errors);
		return errorResponse;
	}
}
