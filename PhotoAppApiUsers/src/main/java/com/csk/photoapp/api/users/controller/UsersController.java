package com.csk.photoapp.api.users.controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csk.photoapp.api.users.service.UsersService;
import com.csk.photoapp.api.users.shared.UsersEntity;
import com.csk.photoapp.api.users.ui.model.CreateAppUsersRequest;
import com.csk.photoapp.api.users.ui.model.LoginRequestModel;
import com.csk.photoapp.api.users.ui.model.UsersResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UsersService service;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private List<UsersResponse> response;
	
	@GetMapping("/status/check")
	public String status() {
		return "Uzumaki Naruto && Uchiha Saske::" + env.getProperty("local.server.port");
	}
	
	@PostMapping()
	public ResponseEntity<UsersResponse> createUser(@Valid @RequestBody CreateAppUsersRequest request) {
		log.info("Request::" + request);
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UsersEntity entity = mapper.map(request, UsersEntity.class);
		entity.setEncryptedPassword(request.getPassword()); 
		UsersEntity serviceResp = service.saveUser(entity);
		UsersResponse response = mapper.map(serviceResp, UsersResponse.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	public List<UsersResponse> getUsers() {
		List<UsersEntity> users = service.getUsers();
		
		response = new LinkedList<UsersResponse>();
		response = Collections.synchronizedList(response);
		users.parallelStream().forEach(
				(entity) -> {
					response.add(mapper.map(entity, UsersResponse.class));
				}
				);
		return response;
	}  
	
	@PostMapping("/login")
	public String authenticate(@RequestBody LoginRequestModel requestModel, HttpServletRequest request, HttpServletResponse response) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestModel.getEmail(), requestModel.getPassword()));
		}
		catch (AuthenticationException e) {
			return "failure";
		}
		
		return "success";
	}
	
}
