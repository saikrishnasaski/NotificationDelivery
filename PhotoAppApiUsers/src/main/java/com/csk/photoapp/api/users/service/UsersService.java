package com.csk.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.csk.photoapp.api.users.dao.UsersDao;
import com.csk.photoapp.api.users.shared.UsersEntity;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsersService implements UsersDetailsService {
	
	@Autowired
	private UsersDao repository;
	
	@Autowired
	@Qualifier(value = "bCryptPasswordEncoder")
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UsersEntity saveUser(UsersEntity entity) {
		entity.setUserId(UUID.randomUUID().toString());
		entity.setEncryptedPassword(bCryptPasswordEncoder.encode(entity.getEncryptedPassword()));
		
		return repository.save(entity);
		
	}

	public List<UsersEntity> getUsers() {
		return repository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("usename: " + username);
		UsersEntity userEntity = repository.findByEmail(username);
		log.info("User Entity: " + userEntity);
		if (userEntity == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(
				userEntity.getEmail(),
				userEntity.getEncryptedPassword(),
				true,
				true,
				true,
				true,
				new ArrayList<>()
				);
	}
	
	public UsersEntity getUserByUserName(String email) {
		UsersEntity userEntity = repository.findByEmail(email);
		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		return userEntity;
	}
}
