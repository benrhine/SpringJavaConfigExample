package com.benrhine.spring.config.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.benrhine.spring.repository.UserRepository;
import com.benrhine.spring.repository.UserRepositoryImpl;
import com.benrhine.spring.service.UserService;
import com.benrhine.spring.service.UserServiceImpl;

@Configuration
public class UserServiceIntConfig {
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
	
	@Bean
	public UserRepository userRepository() {
		return new UserRepositoryImpl();
	}
}
