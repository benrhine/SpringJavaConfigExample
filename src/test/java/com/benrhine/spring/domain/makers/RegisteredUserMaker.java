//package com.benrhine.spring.domain.makers;
//
//import com.benrhine.spring.domain.RegisteredUser;
//import com.benrhine.spring.domain.Role;
//import com.benrhine.spring.util.RoleUtil;
//
//public class RegisteredUserMaker {
//	
//	public static RegisteredUser makeRegisteredUser(final String username) {
//		RegisteredUser ru = new RegisteredUser();
//		
//		ru.setFirstName("Test");
//		ru.setLastName("User");
//		ru.setUsername(username);
//		ru.setPassword("password");
//		ru.setShortUsername("tUser");
//		ru.setEnabled(1);
//		ru.setRole(new Role(RoleUtil.ROLE_USER, ru));
//		
//		return ru;
//	}
//}
