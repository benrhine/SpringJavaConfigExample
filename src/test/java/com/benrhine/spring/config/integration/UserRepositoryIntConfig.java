package com.benrhine.spring.config.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.benrhine.spring.repository.UserRepository;
import com.benrhine.spring.repository.UserRepositoryImpl;

@Configuration
public class UserRepositoryIntConfig {
	@Bean
	public UserRepository userRepository() {
		return new UserRepositoryImpl();
	}
}
