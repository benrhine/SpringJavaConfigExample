package com.benrhine.spring.domain.makers;

import com.benrhine.spring.domain.Role;

public class RoleMaker {

	public static Role makeRole() {
		Role role = new Role();
		
		role.setRole(2L);
		role.setUser(UserMaker.makeUser("test@fake.com"));
		
		return role;
	}
}
