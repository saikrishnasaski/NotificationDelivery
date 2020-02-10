package com.csk.photoapp.api.users.ui.model;

import lombok.Data;

@Data
public class UsersResponse {
	
	private String firstName;
	private String lastName;
	private String userId;
	private String email;
	@Override
	public String toString() {
		return "UsersResponse [firstName=" + firstName + ", lastName=" + lastName + ", userId=" + userId + ", email="
				+ email + "]";
	}
	

}
