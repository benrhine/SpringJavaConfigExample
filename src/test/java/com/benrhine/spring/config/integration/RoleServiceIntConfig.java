package com.benrhine.spring.config.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.benrhine.spring.repository.RoleRepository;
import com.benrhine.spring.repository.RoleRepositoryImpl;
import com.benrhine.spring.service.RoleService;
import com.benrhine.spring.service.RoleServiceImpl;

@Configuration
public class RoleServiceIntConfig {
	@Bean
	public RoleService roleService() {
		return new RoleServiceImpl();
	}
	
	@Bean
	public RoleRepository roleRepository() {
		return new RoleRepositoryImpl();
	}
}
