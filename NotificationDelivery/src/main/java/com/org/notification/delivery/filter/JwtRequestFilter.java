package com.org.notification.delivery.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.org.notification.delivery.exception.UnAuthorizedException;
import com.org.notification.delivery.util.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private InMemoryUserDetailsManager ip;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.startsWith("Bearer ")) {
			String jwtToken = authorization.substring(7);
			
			// validates the JWT Token
			String username = jwtUtil.extractUsername(jwtToken);
			
			if (jwtUtil.isTokenExpired(jwtToken)) {
				throw new UnAuthorizedException("Access Token expired.");
			}
			
		}
		else if (!authorization.startsWith("Basic ")){
			throw new UnAuthorizedException("You are not authorized to access this API.");
		}
		
		filterChain.doFilter(request, response);
	}

}
