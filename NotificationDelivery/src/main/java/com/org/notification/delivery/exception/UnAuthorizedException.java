package com.org.notification.delivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {
	
	private String message;

	public UnAuthorizedException(String message) {
		super(message);
		this.message = message;
	}
	
	

}
