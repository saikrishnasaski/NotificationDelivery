package com.csk.photoapp.api.users.shared;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UsersEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
	private long id;
	
	@Column(name = "FIRST_NAME", nullable=false, length=50)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable=false, length=50)
	private String lastName;
	
	@Column(name = "USER_ID", nullable=false, length=50)
	private String userId;
	
	@Column(name = "ENCRYPTED_PASSWORD", nullable=false, length=100)
	private String encryptedPassword;
	
	@Column(name = "EMAIL", nullable=false, length=200)
	private String email;

	@Override
	public String toString() {
		return "UsersEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userId=" + userId
				+ ", encryptedPassword=" + encryptedPassword + ", email=" + email + "]";
	}
	
	

}
