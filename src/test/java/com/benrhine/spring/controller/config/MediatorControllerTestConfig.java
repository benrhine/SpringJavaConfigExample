package com.benrhine.spring.controller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.benrhine.spring.controller.MediatorController;

@Configuration
public class MediatorControllerTestConfig {
	@Bean
	public MediatorController mediatorController() {
		return new MediatorController();
	}
}
