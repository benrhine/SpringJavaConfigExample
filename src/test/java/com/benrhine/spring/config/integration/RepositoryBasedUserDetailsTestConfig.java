package com.benrhine.spring.config.integration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.benrhine.spring.repository.UserRepository;
import com.benrhine.spring.service.RepositoryBasedUserDetailsService;

@Configuration
public class RepositoryBasedUserDetailsTestConfig {
	
	@Bean
	public RepositoryBasedUserDetailsService detailsService() {
		return new RepositoryBasedUserDetailsService();
	}
	
	@Bean
	public UserRepository userRepo() {
		return Mockito.mock(UserRepository.class);
	}
}
