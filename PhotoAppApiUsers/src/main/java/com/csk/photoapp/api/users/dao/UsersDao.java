package com.csk.photoapp.api.users.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csk.photoapp.api.users.shared.UsersEntity;

@Repository
public interface UsersDao extends JpaRepository<UsersEntity, Long> {
	
	public UsersEntity findByEmail(String email);

}
