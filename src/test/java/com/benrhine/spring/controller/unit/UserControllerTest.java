package com.benrhine.spring.controller.unit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
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
import org.springframework.validation.BindingResult;

import com.benrhine.spring.controller.UserController;
import com.benrhine.spring.controller.config.UserControllerTestConfig;
import com.benrhine.spring.data.mapper.UserMapper;
import com.benrhine.spring.data.transfer.UserDto;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.domain.makers.UserMaker;
import com.benrhine.spring.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UserControllerTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class UserControllerTest {
	@Autowired private UserController userController;
	@Autowired private UserService userService;
	
	@Mock private ModelMap model;
	@Mock private BindingResult result;
	
	private UserDto userDto;
	private User user;
	
	@Before
	public void setupBeforeEveryMethod() throws Exception {
		MockitoAnnotations.initMocks(this);

		user = UserMaker.makeUser("test@fake.com");
		userDto = UserMapper.map(user);
	}
	
	@After
	public void tearDownAfterEveryMethod() throws Exception {
		Mockito.reset(model);
		Mockito.reset(result);
		Mockito.reset(userService);
	}
	
	@Test
	public void addUserPageReturnValue() {
		final String view = userController.addUserPage(model);
		
		assertEquals(view, "user/add-user");
		
		Mockito.verify(model).addAttribute(Mockito.anyString(), Mockito.any(UserDto.class));
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void addingUserReturnValueWhenResultHasError() {
		Mockito.when(result.hasErrors()).thenReturn(true);
		
		final String view = userController.addingUser(userDto, result, model);
		
		assertEquals(view, "user/add-user");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(model, VerificationModeFactory.times(2)).addAttribute(Mockito.anyString(), Mockito.any(UserDto.class));
		Mockito.verify(model).addAttribute("errorMessage", "error.have.occured");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void addingUserReturnValueWhenResultHasNoErrorsAndThrowsConstraintViolation() throws ConstraintViolationException, Exception {
		userDto.setFirstName("Null is not working so I will make myself incredibly long so i trigger the length constraint");
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.doThrow(cve).when(userService).addUser(Mockito.any(User.class));

		final String view = userController.addingUser(userDto, result, model);
		assertEquals(view, "index");
			
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(userService, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", cve.getMessage());
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void addingUserReturnValueWhenResultHasNoErrorsAndThrowsException() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.doThrow(new Exception()).when(userService).addUser(Mockito.any(User.class));
		
		final String view = userController.addingUser(userDto, result, model);
		
		assertEquals(view, "index");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(userService, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", null);
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void addingUserReturnValueWhenResultHasNoErrorsAndIsSuccessful() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(true);
		
		final String view = userController.addingUser(userDto, result, model);
		
		assertEquals(view, "index");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(userService, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("message", "user.added.success");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void addingUserReturnValueWhenResultHasNoErrorsAndIsNotSuccessful() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(false);
		
		final String view = userController.addingUser(userDto, result, model);
		
		assertEquals(view, "index");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(userService, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "user.already.exists");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void editUserPageReturnValueWhenThrowsHibernateException() {
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(userService).findById(1L);
		
		final String view = userController.editUserPage(1L, model);
		
		assertEquals(view, "user/edit-user");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "An error occured accessing the database ...");
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void editUserPageReturnValueWhenThrowsRuntimeException() {
		Mockito.doThrow(new RuntimeException("Exception")).when(userService).findById(1L);
		
		final String view = userController.editUserPage(1L, model);
		
		assertEquals(view, "user/edit-user");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "Exception");
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void editUserPageReturnValueWhenSuccessful() {
		Mockito.when(userService.findById(1L)).thenReturn(user);
		
		final String view = userController.editUserPage(1L, model);
		
		assertEquals(view, "user/edit-user");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute(Mockito.anyString(), Mockito.any(UserDto.class));
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void editingUserReturnValueWhenResultHasError() {
		Mockito.when(result.hasErrors()).thenReturn(true);
		
		final String view = userController.editingUser(userDto, result, 1L, model);
		
		assertEquals(view, "user/edit-user");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(model, VerificationModeFactory.times(2)).addAttribute(Mockito.anyString(), Mockito.any(UserDto.class));
		Mockito.verify(model).addAttribute("errorMessage", "error.have.occured");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void editingUserReturnValueWhenResultHasNoErrorsAndThrowsConstraintViolation() throws ConstraintViolationException, Exception {
		userDto.setFirstName("Null is not working so I will make myself incredibly long so i trigger the length constraint");
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.doThrow(cve).when(userService).updateUser(Mockito.any(User.class));

		final String view = userController.editingUser(userDto, result, 1L, model);
		assertEquals(view, "admin/admin");
			
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(userService, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", cve.getMessage());
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void editingUserReturnValueWhenResultHasNoErrorsAndThrowsException() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.doThrow(new Exception()).when(userService).updateUser(Mockito.any(User.class));
		
		final String view = userController.editingUser(userDto, result, 1L, model);
		
		assertEquals(view, "admin/admin");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(userService, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", null);
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void editingUserReturnValueWhenResultHasNoErrorsAndIsSuccessful() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(userService.updateUser(Mockito.any(User.class))).thenReturn(true);
		
		final String view = userController.editingUser(userDto, result, 1L, model);
		
		assertEquals(view, "admin/admin");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(userService, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("message", "user.update.success");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void editingUserReturnValueWhenResultHasNoErrorsAndIsNotSuccessful() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(userService.updateUser(Mockito.any(User.class))).thenReturn(false);
		
		final String view = userController.editingUser(userDto, result, 1L, model);
		
		assertEquals(view, "admin/admin");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(userService, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "user.does.not.exists");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void deleteUserPageReturnValueWhenThrowsHibernateException() {
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(userService).findById(1L);
		
		final String view = userController.deleteUserPage(1L, model);
		
		assertEquals(view, "redirect:/user/list");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "An error occured accessing the database ...");
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void deleteUserPageReturnValueWhenThrowsRuntimeException() {
		Mockito.doThrow(new RuntimeException("Exception")).when(userService).findById(1L);
		
		final String view = userController.deleteUserPage(1L, model);
		
		assertEquals(view, "redirect:/user/list");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "Exception");
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void deleteUserPageReturnValueWhenThrowsException() throws Exception {
		Mockito.when(userService.findById(1L)).thenReturn(user);
		Mockito.doThrow(new Exception("Exception")).when(userService).deleteUser(Mockito.any(User.class));
		
		final String view = userController.deleteUserPage(1L, model);
		
		assertEquals(view, "redirect:/user/list");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(userService, VerificationModeFactory.times(1)).deleteUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "Exception");
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void deleteUserPageReturnValueWhenSuccessful() throws Exception {
		Mockito.when(userService.findById(1L)).thenReturn(user);
		Mockito.when(userService.deleteUser(Mockito.any(User.class))).thenReturn(true);
		
		final String view = userController.deleteUserPage(1L, model);
		
		assertEquals(view, "redirect:/user/list");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(userService, VerificationModeFactory.times(1)).deleteUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("message", "user.delete.success");
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void deleteUserPageReturnValueWhenNotSuccessful() throws Exception {
		Mockito.when(userService.findById(1L)).thenReturn(user);
		Mockito.when(userService.deleteUser(Mockito.any(User.class))).thenReturn(false);
		
		final String view = userController.deleteUserPage(1L, model);
		
		assertEquals(view, "redirect:/user/list");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(userService, VerificationModeFactory.times(1)).deleteUser(Mockito.any(User.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "user.delete.error");
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void listOfUserPageReturnValueWhenThrowsHibernateException() {
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(userService).findAllUsers();
		
		final String view = userController.listOfUsers(model);
		
		assertEquals(view, "user/list-users");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findAllUsers();
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "An error occured accessing the database ...");
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void listOfUserPageReturnValueWhenThrowsRuntimeException() {
		Mockito.doThrow(new RuntimeException("Exception")).when(userService).findAllUsers();
		
		final String view = userController.listOfUsers(model);
		
		assertEquals(view, "user/list-users");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findAllUsers();
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "Exception");
		Mockito.verifyNoMoreInteractions(userService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void listOfUserPageReturnValueWhenSuccessful() {
		List<User> lst = new ArrayList<User>();
		lst.add(user);
		
		Mockito.when(userService.findAllUsers()).thenReturn(lst);
		
		final String view = userController.listOfUsers(model);
		
		assertEquals(view, "user/list-users");
		
		Mockito.verify(userService, VerificationModeFactory.times(1)).findAllUsers();
		Mockito.verifyNoMoreInteractions(userService);
	}
}
