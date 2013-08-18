package com.benrhine.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.DelegatingFilterProxy;
 
@Configuration
@ImportResource({"classpath:/spring-java-example-security.xml"})
public class SecurityConfig {
	@Bean
	public DelegatingFilterProxy springSecurityFilterChain() {
		DelegatingFilterProxy filterProxy = new DelegatingFilterProxy();
		return filterProxy;
	}	
}