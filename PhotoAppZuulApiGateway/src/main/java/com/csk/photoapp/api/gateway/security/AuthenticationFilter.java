package com.csk.photoapp.api.gateway.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;


public class AuthenticationFilter extends BasicAuthenticationFilter {
	
	private Environment env;

	public AuthenticationFilter(AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint, Environment env) {
		super(authenticationManager, authenticationEntryPoint);
		this.env = env;
	}



	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String authenticationHeader = request.getHeader(env.getProperty("api.request.security.header"));
		
		if (authenticationHeader == null || !authenticationHeader.startsWith(env.getProperty("api.request.security.header-prefix"))) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(authenticationHeader);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthentication(final String authenticationHeader) {
		if (authenticationHeader == null) {
			return null;
		}
		
		String token = authenticationHeader.replace(env.getProperty("api.request.security.header-prefix"), "");
		String userId = Jwts.parser()
		.setSigningKey(env.getProperty("token.secret"))
		.parseClaimsJws(token)
		.getBody()
		.getSubject();
		
		if (userId == null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
		
		
	}
	
	
	

}
