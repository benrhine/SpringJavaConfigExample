package com.benrhine.spring.domain.makers;

import com.benrhine.spring.domain.Role;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.util.RoleUtil;

public class UserMaker {
	public static User makeUser(final String username) {
		User u = new User();
		
		u.setFirstName("Test");
		u.setLastName("User");
		u.setUsername(username);
		u.setPassword("password");
		u.setShortUsername("tUser");
		u.setEnabled(1);
		u.setRole(new Role(RoleUtil.ROLE_USER, u));
		
		return u;
	}
}
