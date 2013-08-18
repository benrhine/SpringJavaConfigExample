package com.benrhine.spring.controller.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.benrhine.spring.controller.UserController;
import com.benrhine.spring.service.UserService;

@Configuration
public class UserControllerTestConfig {
	@Bean
	public UserController userController() {
		return new UserController();
	}
	
	@Bean
	public UserService userService() {
		return Mockito.mock(UserService.class);
	}
}
