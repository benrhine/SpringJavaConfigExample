package com.benrhine.spring.controller.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import com.benrhine.spring.controller.AccessController;
import com.benrhine.spring.service.UserService;

@Configuration
public class AccessControllerTestConfig {
	@Bean
	public AccessController accessController() {
		return new AccessController();
	}
	
	@Bean
	public AuthenticationManager authManager() {
		return Mockito.mock(AuthenticationManager.class);
	}
	
	@Bean
	public UserService userService() {
		return Mockito.mock(UserService.class);
	}
}
