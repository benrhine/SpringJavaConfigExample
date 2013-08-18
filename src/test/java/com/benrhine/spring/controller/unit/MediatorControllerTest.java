package com.benrhine.spring.controller.unit;

import static org.junit.Assert.assertEquals;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.ui.ModelMap;

import com.benrhine.spring.controller.MediatorController;
import com.benrhine.spring.controller.config.MediatorControllerTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { MediatorControllerTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class MediatorControllerTest {
	@Autowired private MediatorController mediatorController;
	
	@Mock private ModelMap model;
	@Mock private Principal principal;
	@Mock private HttpServletRequest request;
	
	@Before
	public void setupBeforeEveryMethod() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDownAfterEveryMethod() throws Exception {
		Mockito.reset(model);
		Mockito.reset(principal);
		Mockito.reset(request);
	}
	
	@Test
	public void mainPageReturnValueWhenAuthenticatedAsUser() {
		Mockito.when(request.isUserInRole("ROLE_ADMIN")).thenReturn(false);
		
		final String view = mediatorController.mainPage(model, principal, request);
		
		assertEquals(view, "index");
		
		Mockito.verify(model).addAttribute("username", principal.getName());
		Mockito.verify(model).addAttribute("message", "Welcome to the Spring XML Example App");
		Mockito.verify(request, VerificationModeFactory.times(1)).isUserInRole("ROLE_ADMIN");
		Mockito.verifyNoMoreInteractions(request);
	}
	
	@Test
	public void mainPageReturnValueWhenAuthenticatedAsAdmin() {
		Mockito.when(request.isUserInRole("ROLE_ADMIN")).thenReturn(true);
		
		final String view = mediatorController.mainPage(model, principal, request);
		
		assertEquals(view, "admin/admin");
		
		Mockito.verify(model).addAttribute("username", principal.getName());
		Mockito.verify(model).addAttribute("message", "Welcome Administrator, what is thy bidding ...");
		Mockito.verify(request, VerificationModeFactory.times(1)).isUserInRole("ROLE_ADMIN");
		Mockito.verifyNoMoreInteractions(request);
	}
}
