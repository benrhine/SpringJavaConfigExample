package com.benrhine.spring.controller.unit;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.benrhine.spring.controller.AccessController;
import com.benrhine.spring.controller.config.AccessControllerTestConfig;
import com.benrhine.spring.data.transfer.UserDto;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AccessControllerTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class AccessControllerTest {
	@Autowired private AuthenticationManager authManager;
	@Autowired private AccessController accessController;
	@Autowired private UserService userService;
	
	@Mock private ModelMap model;
	@Mock private HttpSession session;
	@Mock private HttpServletRequest request;
	@Mock private BindingResult result;
	
	private UserDto userDto;
	
	@Before
	public void setupBeforeEveryMethod() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		userDto = new UserDto();
	}
	
	@After
	public void tearDownAfterEveryMethod() throws Exception {
		Mockito.reset(model);
		Mockito.reset(session);
		Mockito.reset(request);
		Mockito.reset(result);
	}
	
	@Test
	public void loginReturnValueWhenErrorIsEmpty() {
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).thenReturn(null);
		
		final String view = accessController.login(model, request);
		
		assertEquals(view, "access/login");
		
		Mockito.verify(request, VerificationModeFactory.times(1)).getSession();
		Mockito.verify(session, VerificationModeFactory.times(1)).getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		Mockito.verifyNoMoreInteractions(request);
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void loginReturnValueWhenErrorIsNotEmptyAndExceptionIsUnknown() {
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).thenReturn(new Exception("Test Exception"));
		
		final String view = accessController.login(model, request);
		
		assertEquals(view, "access/login");
		
		Mockito.verify(model).addAttribute("errorMessage", "Test Exception");
		Mockito.verify(request, VerificationModeFactory.times(1)).getSession();
		Mockito.verify(session, VerificationModeFactory.times(1)).getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		Mockito.verifyNoMoreInteractions(model);
		Mockito.verifyNoMoreInteractions(request);
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void loginReturnValueWhenErrorIsNotEmptyAndExceptionIsNullPointer() {
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).thenReturn(new Exception("java.lang.NullPointerException"));
		
		final String view = accessController.login(model, request);
		
		assertEquals(view, "access/login");
		
		Mockito.verify(model).addAttribute("errorMessage", "login.failure.username.notfound");
		Mockito.verify(request, VerificationModeFactory.times(1)).getSession();
		Mockito.verify(session, VerificationModeFactory.times(1)).getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		Mockito.verifyNoMoreInteractions(model);
		Mockito.verifyNoMoreInteractions(request);
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void loginReturnValueWhenErrorIsNotEmptyAndExceptionIsBadCredentials() {
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).thenReturn(new Exception("Bad Credentials"));
		
		final String view = accessController.login(model, request);
		
		assertEquals(view, "access/login");
		
		Mockito.verify(model).addAttribute("errorMessage", "login.failure.username.password.incorrect");
		Mockito.verify(request, VerificationModeFactory.times(1)).getSession();
		Mockito.verify(session, VerificationModeFactory.times(1)).getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		Mockito.verifyNoMoreInteractions(model);
		Mockito.verifyNoMoreInteractions(request);
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void loginErrorReturnValue() {
		final String view = accessController.loginError(model);
		
		assertEquals(view, "access/login");
	}
	
	@Test
	public void logoutReturnValue() {
		final String view = accessController.logout(model);
		
		assertEquals(view, "access/login");
	}
	
	@Test
	public void logoutSuccessReturnValue() {
		final String view = accessController.logoutSuccess(model);
		
		assertEquals(view, "access/login");
	}
	
	@Test
	public void logoutErrorReturnValue() {
		final String view = accessController.logoutError(model);
		
		assertEquals(view, null);
	}
	
	@Test
	public void deniedReturnValue() {
		final String view = accessController.denied(model);
		
		assertEquals(view, "access/denied");
		
		Mockito.verify(model).addAttribute("errorMessage", "access.denied.message");
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void signupReturnValue() {
		final String view = accessController.signup(model);
		
		assertEquals(view, "access/signup");
		
		Mockito.verify(model).addAttribute(Mockito.anyString(), Mockito.any(UserDto.class));
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void createAccountReturnValueWhenBindingResultHasErrors() {
		Mockito.when(result.hasErrors()).thenReturn(true);
		
		final String view = accessController.createAccount(userDto, result, request, model);
		
		assertEquals(view, "access/signup");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		// Not sure why this is needed two times, it should only be needed once.
		Mockito.verify(model, VerificationModeFactory.times(2)).addAttribute(Mockito.anyString(), Mockito.any(UserDto.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "error.have.occured");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void createAccountReturnValueWhenBindingResultHasNoErrorsUserDoesNotExistsAndUsernamesDoNotMatch() {
		Mockito.when(result.hasErrors()).thenReturn(false);
		
		userDto.setUserName("test@fake.com");
		userDto.setReUserName("user@fake.com");
		
		final String view = accessController.createAccount(userDto, result, request, model);
		
		assertEquals(view, "access/signup");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "signup.invalid.username.notmatching");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void createAccountReturnValueWhenBindingResultHasNoErrorsUserDoesNotExistsUsernamesMatchAndPasswordsDoNotMatch() {
		Mockito.when(result.hasErrors()).thenReturn(false);
		
		userDto.setUserName("test@fake.com");
		userDto.setReUserName("test@fake.com");
		userDto.setUserPass("pass");
		userDto.setReUserPass("nopass");
		
		final String view = accessController.createAccount(userDto, result, request, model);
		
		assertEquals(view, "access/signup");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "signup.invalid.password.notmatching");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void createAccountReturnValueWhenBindingResultHasNoErrorsUserDoesNotExistsUsernamesMatchPasswordsMatch() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(false);
		
		userDto.setUserName("test@fake.com");
		userDto.setReUserName("test@fake.com");
		userDto.setUserPass("pass");
		userDto.setReUserPass("pass");
		
		final String view = accessController.createAccount(userDto, result, request, model);
		
		assertEquals(view, "access/signup");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		/**
		 * I think the reason VerificationModeFactory is called 3 times is due
		 * to the fact that there are 3 if tests before the user is added.
		 */
		Mockito.verify(userService, VerificationModeFactory.times(3)).addUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(2)).addAttribute(Mockito.anyString(), Mockito.any(User.class));
		Mockito.verify(model).addAttribute("errorMessage", "signup.invalid.username.duplicate");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void createAccountReturnValueWhenBindingResultHasNoErrorsUserDoesNotExistsUsernamesMatchPasswordsMatchAndIsSuccessful() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(true);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.doNothing().when(session).setAttribute(Mockito.anyString(), Mockito.any());
		
		userDto.setUserName("test@fake.com");
		userDto.setReUserName("test@fake.com");
		userDto.setUserPass("pass");
		userDto.setReUserPass("pass");
		
		final String view = accessController.createAccount(userDto, result, request, model);
		
		assertEquals(view, "index");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(userService, VerificationModeFactory.times(2)).addUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(3)).addAttribute(Mockito.anyString(), Mockito.any(User.class));
		Mockito.verify(model).addAttribute("status", "User was successfully added.");
		Mockito.verify(model).addAttribute("message", "Thank you for your registration.");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void createAccountReturnValueWhenBindingResultHasNoErrorsUserDoesNotExistsUsernamesMatchPasswordsMatchAndThrowsException() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(true);
		
		userDto.setUserName("test@fake.com");
		userDto.setReUserName("test@fake.com");
		userDto.setUserPass("pass");
		userDto.setReUserPass("pass");
		
		final String view = accessController.createAccount(userDto, result, request, model);
		
		assertEquals(view, "access/signup");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(userService, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(4)).addAttribute(Mockito.anyString(), Mockito.any(User.class));
		Mockito.verify(model).addAttribute("status", "User was successfully added.");
		Mockito.verify(model).addAttribute("message", "Thank you for your registration.");
		Mockito.verify(model).addAttribute("errorMessage", "could.not.create.new.user");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
}
