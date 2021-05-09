package com.org.notification.delivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.org.notification.delivery.model.Error;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Unauthorized.class) 
	public ResponseEntity handle(Unauthorized ex) {
		ex.printStackTrace();
		return new ResponseEntity<>("Error", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UnAuthorizedException.class) 
	public ResponseEntity handleUnAuthorizedException(Exception ex) {
		ex.printStackTrace();
		Error error = new Error(HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}

}
