package com.csk.photoapp.api.users.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.csk.photoapp.api.users.service.UsersDetailsService;
import com.csk.photoapp.api.users.shared.UsersEntity;
import com.csk.photoapp.api.users.ui.model.LoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UsersDetailsService usersDetailsService;
	private Environment environment;
	
	public AuthenticationFilter(UsersDetailsService usersDetailsService, Environment environment, AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
		this.usersDetailsService = usersDetailsService;
		this.environment = environment;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			log.info("csk  ");
			LoginRequestModel creds = new ObjectMapper()
					.readValue(request.getInputStream(), LoginRequestModel.class);
			log.info("email: " + creds.getEmail());
			log.info("password: " + creds.getPassword());
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getEmail(), 
							creds.getPassword(),
							new ArrayList<>()
							)
					);
		}
		catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.info("Auth Success");
		log.info("Token Expiration Time", environment.getProperty("token.expiration_time"));
		UsersEntity userEntity = usersDetailsService.getUserByUserName(((User) authResult.getPrincipal()).getUsername());
		String token = Jwts.builder().setSubject(userEntity.getUserId())
					  .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
					  .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
					  .compact();
		
		log.info("Token: " + token);
		response.addHeader("Token Expiration Time", environment.getProperty("token.expiration_time"));
		response.addHeader("token", token);
		response.addHeader("userId", userEntity.getUserId());
	}
	
	

}
