package com.csk.photoapp.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateAppUsersRequest {
	
	@NotNull(message = "First Name is required")
	@Size(min = 5, message = "First Name cannot be greater than 5 characters")
	private String firstName;
	
	@NotNull(message = "Last Name is required")
	@Size(min = 5, message = "Last Name cannot be greater than 5 characters")
	private String lastName;
	
	@NotNull(message = "Password is required")
	@Size(min = 8, message = "Password cannot be greater than 5 characters")
	private String password;
	
	@NotNull(message = "Email is required")
	@Email
	private String email;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
