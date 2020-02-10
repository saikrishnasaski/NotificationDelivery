package com.csk.photoapp.api.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.csk.photoapp.api.users.service.UsersDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private UsersDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		/*
		 * httpSecurity.authorizeRequests().antMatchers("/").permitAll().and().
		 * authorizeRequests() .antMatchers("/h2-console/**").permitAll();
		 * httpSecurity.csrf().disable();
		 * httpSecurity.headers().frameOptions().disable();
		 */

		/*
		 * httpSecurity.authorizeRequests(). antMatchers("/users/**").permitAll() .and()
		 * .authorizeRequests() .antMatchers("/h2-console/**").permitAll() .and()
		 * .authorizeRequests() .antMatchers("/**").authenticated() .and()
		 * .addFilter(authenticationFilter());
		 */
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
		
		httpSecurity.authorizeRequests()
					.antMatchers("/**").permitAll()
					.and()
					.addFilter(authenticationFilter());

	}
	
	private AuthenticationFilter authenticationFilter() throws Exception {
		AuthenticationFilter filter = new AuthenticationFilter(userDetailsService, env, authenticationManager());
		return filter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	

}
