package com.benrhine.spring.service.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.benrhine.spring.config.integration.RepositoryBasedUserDetailsTestConfig;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.domain.makers.UserMaker;
import com.benrhine.spring.repository.UserRepository;
import com.benrhine.spring.service.RepositoryBasedUserDetailsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RepositoryBasedUserDetailsTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RepositoryBasedUserDetailsServiceTest {
	@Autowired private RepositoryBasedUserDetailsService detailsService;
	@Autowired private UserRepository userRepo;
	
	private User user;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		user = UserMaker.makeUser("test@fake.com");
	}
	
	@Test(expected = RuntimeException.class)
	public void loadUserByUserNameFromDatabaseThrowsException() throws RuntimeException {
		detailsService.loadUserByUsername(null);
	}
	
	@Test
	public void loadUserByUserNameFromDataBaseSuccessfullyReturnsUser() {
		Mockito.when(userRepo.findByUsername("test@fake.com")).thenReturn(user);
		
		UserDetails ud = detailsService.loadUserByUsername("test@fake.com");
		
		assertNotNull(ud);
		assertEquals(ud.getUsername(), user.getUsername());
		
		Mockito.verify(userRepo, VerificationModeFactory.times(3)).findByUsername(Mockito.anyString());
		Mockito.reset(userRepo);
	}
	
	@Test(expected = RuntimeException.class)
	public void loadUserByUserNameFromDataBaseUserNotFound() {
		Mockito.when(userRepo.findByUsername("test@fake.com")).thenReturn(null);
		
		UserDetails ud = detailsService.loadUserByUsername("test@fake.com");
		
		assertNull(ud);
		
		Mockito.verify(userRepo, VerificationModeFactory.times(2)).findByUsername(Mockito.anyString());
		Mockito.reset(userRepo);
	}
}
