package com.org.notification.delivery.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController {
	
	public class HelloResponse {
		String response;

		public String getResponse() {
			return response;
		}

		public void setResponse(String response) {
			this.response = response;
		}
		
	}
	
	@GetMapping(value = "/requestToPay/hello", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<HelloResponse> hello() {
		System.out.println("Hello");
		HelloResponse response = new HelloResponse();
		response.setResponse("Hello");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
