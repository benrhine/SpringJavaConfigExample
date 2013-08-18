package com.benrhine.spring.config.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.benrhine.spring.repository.RoleRepository;
import com.benrhine.spring.repository.RoleRepositoryImpl;

@Configuration
public class RoleRepositoryIntConfig {
	@Bean
	public RoleRepository roleRepository() {
		return new RoleRepositoryImpl();
	}
}
