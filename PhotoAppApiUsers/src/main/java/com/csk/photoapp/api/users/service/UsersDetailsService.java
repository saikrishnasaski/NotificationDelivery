package com.csk.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.csk.photoapp.api.users.shared.UsersEntity;

public interface UsersDetailsService extends UserDetailsService {
	
	public UsersEntity getUserByUserName(String email);

}
