package com.application.weshorten.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(URLException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidUrl(URLException e) {
		return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(URLNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidUrl(URLNotFoundException e) {
		return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneric(Exception e) {
		return buildErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", Instant.now().toString());
		body.put("status", status.value());
		body.put("error", status.getReasonPhrase());
		body.put("message", message);
		return new ResponseEntity<>(body, status);
	}
}
