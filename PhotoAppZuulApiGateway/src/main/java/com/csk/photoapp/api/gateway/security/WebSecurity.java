package com.csk.photoapp.api.gateway.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private Environment env;
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	public WebSecurity(Environment env, AuthenticationEntryPoint authenticationEntryPoint) {
		this.env = env;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.headers().frameOptions().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests()
			.antMatchers(env.getProperty("api.zuul.actuator.path")).permitAll()
			.antMatchers(env.getProperty("api.h2-console")).permitAll()
			.antMatchers(HttpMethod.POST, env.getProperty("api.registration.path")).permitAll()
			.antMatchers(HttpMethod.POST, env.getProperty("api.login.path")).permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(new AuthenticationFilter(authenticationManager(), authenticationEntryPoint, env));
	}
	
	

}
