package com.benrhine.spring.controller.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.benrhine.spring.controller.RoleController;
import com.benrhine.spring.service.RoleService;

@Configuration
public class RoleControllerTestConfig {
	@Bean
	public RoleController roleController() {
		return new RoleController();
	}
	
	@Bean
	public RoleService roleService() {
		return Mockito.mock(RoleService.class);
	}
}
