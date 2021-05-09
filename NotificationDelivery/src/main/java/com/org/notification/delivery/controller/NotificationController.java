package com.org.notification.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.org.notification.delivery.model.AuthenticationResponse;
import com.org.notification.delivery.util.JwtUtil;

@RestController
public class NotificationController {

	@Autowired
	private JwtUtil jwtTokenUtil;

	@RequestMapping(path = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<AuthenticationResponse> createToken() {
		System.out.println("Create Token....");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}

}
