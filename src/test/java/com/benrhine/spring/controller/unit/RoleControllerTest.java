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

import com.benrhine.spring.controller.RoleController;
import com.benrhine.spring.controller.config.RoleControllerTestConfig;
import com.benrhine.spring.domain.Role;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.domain.makers.RoleMaker;
import com.benrhine.spring.domain.makers.UserMaker;
import com.benrhine.spring.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RoleControllerTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class RoleControllerTest {
	@Autowired RoleController roleController;
	@Autowired RoleService roleService;
	
	@Mock private ModelMap model;
	@Mock private BindingResult result;
	@Mock private BindingResult userResult;
	
	private Role role;
	private User user;
	
	@Before
	public void setupBeforeEveryMethod() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		role = RoleMaker.makeRole();
		user = UserMaker.makeUser("test@fake.com");
	}
	
	@After
	public void tearDownAfterEveryMethod() throws Exception {
		Mockito.reset(model);
		Mockito.reset(result);
		Mockito.reset(userResult);
		Mockito.reset(roleService);
	}
	
	@Test
	public void addRolePageReturnValue() {
		final String view = roleController.addRolePage(model);
		
		assertEquals(view, "role/add-role");
		
		Mockito.verify(model).addAttribute(Mockito.anyString(), Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void addingRoleReturnValueWhenResultHasError() {
		Mockito.when(result.hasErrors()).thenReturn(true);
		
		final String view = roleController.addingRole(role, result, model);
		
		assertEquals(view, "role/add-role");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(model, VerificationModeFactory.times(2)).addAttribute(Mockito.anyString(), Mockito.any(Role.class));
		Mockito.verify(model).addAttribute("errorMessage", "error.have.occured");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void addingRoleReturnValueWhenResultHasNoErrorsAndThrowsConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.doThrow(cve).when(roleService).addRole(Mockito.any(Role.class));

		final String view = roleController.addingRole(role, result, model);
		assertEquals(view, "admin/admin");
			
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(roleService, VerificationModeFactory.times(1)).addRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", cve.getMessage());
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void addingRoleReturnValueWhenResultHasNoErrorsAndThrowsException() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.doThrow(new Exception()).when(roleService).addRole(Mockito.any(Role.class));
		
		final String view = roleController.addingRole(role, result, model);
		
		assertEquals(view, "admin/admin");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(roleService, VerificationModeFactory.times(1)).addRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", null);
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void addingRoleReturnValueWhenResultHasNoErrorsAndIsSuccessful() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(roleService.addRole(Mockito.any(Role.class))).thenReturn(true);
		
		final String view = roleController.addingRole(role, result, model);
		
		assertEquals(view, "admin/admin");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(roleService, VerificationModeFactory.times(1)).addRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("message", "role.added.success");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void addingRoleReturnValueWhenResultHasNoErrorsAndIsNotSuccessful() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(roleService.addRole(Mockito.any(Role.class))).thenReturn(false);
		
		final String view = roleController.addingRole(role, result, model);
		
		assertEquals(view, "admin/admin");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(roleService, VerificationModeFactory.times(1)).addRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "role.already.exists");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void editRolePageReturnValueWhenThrowsHibernateException() {
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(roleService).findById(1L);
		
		final String view = roleController.editRolePage(1L, model);
		
		assertEquals(view, "role/edit-role");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "An error occured accessing the database ...");
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void editRolePageReturnValueWhenThrowsRuntimeException() {
		Mockito.doThrow(new RuntimeException("Exception")).when(roleService).findById(1L);
		
		final String view = roleController.editRolePage(1L, model);
		
		assertEquals(view, "role/edit-role");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "Exception");
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void editRolePageReturnValueWhenSuccessful() {
		Mockito.when(roleService.findById(1L)).thenReturn(role);
		
		final String view = roleController.editRolePage(1L, model);
		
		assertEquals(view, "role/edit-role");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(model, VerificationModeFactory.times(2)).addAttribute(Mockito.anyString(), Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(2)).addAttribute(Mockito.anyString(), Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void editingRoleReturnValueWhenResultHasError() {
		Mockito.when(result.hasErrors()).thenReturn(true);
		
		final String view = roleController.editingRole(role, result, user, userResult, 1L, model);
		
		assertEquals(view, "role/edit-role");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(model, VerificationModeFactory.times(2)).addAttribute(Mockito.anyString(), Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "error.have.occured");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void editingRoleReturnValueWhenResultHasNoErrorsAndThrowsConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.doThrow(cve).when(roleService).updateRole(Mockito.any(Role.class));

		final String view = roleController.editingRole(role, result, user, userResult, 1L, model);
		assertEquals(view, "admin/admin");
			
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(roleService, VerificationModeFactory.times(1)).updateRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", cve.getMessage());
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void editingRoleReturnValueWhenResultHasNoErrorsAndThrowsException() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.doThrow(new Exception()).when(roleService).updateRole(Mockito.any(Role.class));
		
		final String view = roleController.editingRole(role, result, user, userResult, 1L, model);
		
		assertEquals(view, "admin/admin");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(roleService, VerificationModeFactory.times(1)).updateRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", null);
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void editingRoleReturnValueWhenResultHasNoErrorsAndIsSuccessful() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(roleService.updateRole(Mockito.any(Role.class))).thenReturn(true);
		
		final String view = roleController.editingRole(role, result, user, userResult, 1L, model);
		
		assertEquals(view, "admin/admin");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(roleService, VerificationModeFactory.times(1)).updateRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("message", "role.update.success");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test 
	public void editingRoleReturnValueWhenResultHasNoErrorsAndIsNotSuccessful() throws ConstraintViolationException, Exception {
		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(roleService.updateRole(Mockito.any(Role.class))).thenReturn(false);
		
		final String view = roleController.editingRole(role, result, user, userResult, 1L, model);
		
		assertEquals(view, "admin/admin");
		
		Mockito.verify(result, VerificationModeFactory.times(1)).hasErrors();
		Mockito.verify(roleService, VerificationModeFactory.times(1)).updateRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "role.does.not.exists");
		Mockito.verifyNoMoreInteractions(result);
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void deleteRolePageReturnValueWhenThrowsHibernateException() {
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(roleService).findById(1L);
		
		final String view = roleController.deleteRolePage(1L, model);
		
		assertEquals(view, "redirect:/role/list");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "An error occured accessing the database ...");
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void deleteRolePageReturnValueWhenThrowsRuntimeException() {
		Mockito.doThrow(new RuntimeException("Exception")).when(roleService).findById(1L);
		
		final String view = roleController.deleteRolePage(1L, model);
		
		assertEquals(view, "redirect:/role/list");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "Exception");
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void deleteRolePageReturnValueWhenThrowsException() throws Exception {
		Mockito.when(roleService.findById(1L)).thenReturn(role);
		Mockito.doThrow(new Exception("Exception")).when(roleService).deleteRole(Mockito.any(Role.class));
		
		final String view = roleController.deleteRolePage(1L, model);
		
		assertEquals(view, "redirect:/role/list");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(roleService, VerificationModeFactory.times(1)).deleteRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "Exception");
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void deleteRolePageReturnValueWhenSuccessful() throws Exception {
		Mockito.when(roleService.findById(1L)).thenReturn(role);
		Mockito.when(roleService.deleteRole(Mockito.any(Role.class))).thenReturn(true);
		
		final String view = roleController.deleteRolePage(1L, model);
		
		assertEquals(view, "redirect:/role/list");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(roleService, VerificationModeFactory.times(1)).deleteRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("message", "role.delete.success");
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void deleteRolePageReturnValueWhenNotSuccessful() throws Exception {
		Mockito.when(roleService.findById(1L)).thenReturn(role);
		Mockito.when(roleService.deleteRole(Mockito.any(Role.class))).thenReturn(false);
		
		final String view = roleController.deleteRolePage(1L, model);
		
		assertEquals(view, "redirect:/role/list");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(roleService, VerificationModeFactory.times(1)).deleteRole(Mockito.any(Role.class));
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "role.delete.error");
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void listOfRolesPageReturnValueWhenThrowsHibernateException() {
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(roleService).findAllRoles();
		
		final String view = roleController.listOfRoles(model);
		
		assertEquals(view, "role/list-roles");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findAllRoles();
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "An error occured accessing the database ...");
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void listOfRolesPageReturnValueWhenThrowsRuntimeException() {
		Mockito.doThrow(new RuntimeException("Exception")).when(roleService).findAllRoles();
		
		final String view = roleController.listOfRoles(model);
		
		assertEquals(view, "role/list-roles");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findAllRoles();
		Mockito.verify(model, VerificationModeFactory.times(1)).addAttribute("errorMessage", "Exception");
		Mockito.verifyNoMoreInteractions(roleService);
		Mockito.verifyNoMoreInteractions(model);
	}
	
	@Test
	public void listOfRolesPageReturnValueWhenSuccessful() {
		List<Role> lst = new ArrayList<Role>();
		lst.add(role);
		
		Mockito.when(roleService.findAllRoles()).thenReturn(lst);
		
		final String view = roleController.listOfRoles(model);
		
		assertEquals(view, "role/list-roles");
		
		Mockito.verify(roleService, VerificationModeFactory.times(1)).findAllRoles();
		Mockito.verifyNoMoreInteractions(roleService);
	}
}
