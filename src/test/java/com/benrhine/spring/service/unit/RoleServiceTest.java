package com.benrhine.spring.service.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.benrhine.spring.domain.Role;
import com.benrhine.spring.domain.makers.RoleMaker;
import com.benrhine.spring.repository.RoleRepository;
import com.benrhine.spring.service.RoleService;
import com.benrhine.spring.service.RoleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class RoleServiceTest {
	/**
	 * @Mock annotation is used as opposed to importing configuration classes
	 * with the @ContextConfiguration(classes = { TestConfig.class }) as it is
	 * much cleaner and does the same thing.
	 */
	@Mock RoleRepository roleRepository;
	
	/**
	 * Use @InjectMocks to dynamically insert your mocked objects into the
	 * "LIVE" object you are testing.
	 */
	@InjectMocks private static RoleService roleService;

	/**
	 * Private domain object for testing.
	 */
	private Role role;
	
	/**
	 * Prior to the execution of tests instantiate the object that is to be
	 * tested.
	 * 
	 * Note: This executes ONLY ONCE.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void oneTimeSetup() throws Exception {
		roleService = new RoleServiceImpl();
	}
	
	/**
	 * Setup that occurs before every single test. Sets up mocks and
	 * creates a populated object for testing.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setupBeforeEveryMethod() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		role = RoleMaker.makeRole();
	}
	
	/**
	 * Tear down that occurs after every single test. Resets mocked
	 * objects to their original state.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDownAfterEveryMethod() throws Exception {
		Mockito.reset(roleRepository);
	}
	
	/**
	 * Add New Role Tests: Should cover throwing a new Exception.
	 * 
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void addNewRoleWhenRoleIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		roleService.addRole(null);
	}
	
//	@Test(expected = ConstraintViolationException.class)
//	public void addNewRoleWhenRoleIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new role ... ", null, null);	
//		Mockito.doThrow(cve).when(roleRepository).addRole(Mockito.any(Role.class));
//		
//		role.setRole(null);
//		roleService.addRole(role);
//			
//		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).addRole(Mockito.any(Role.class));
//		Mockito.verifyNoMoreInteractions(roleRepository);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void addNewRoleWhenUserIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new role ... ", null, null);	
//		Mockito.doThrow(cve).when(roleRepository).addRole(Mockito.any(Role.class));
//	
//		role.setUser(null);
//		roleService.addRole(role);
//			
//		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).addRole(Mockito.any(Role.class));
//		Mockito.verifyNoMoreInteractions(roleRepository);
//	}
	
	/**
	 * Add New Role Tests: Add New Role is successful.
	 * @throws ConstraintViolationException 
	 * @throws Exception 
	 */
	
	@Test
	public void addNewRoleWhenRoleIsValidAndDoesNotAlreadyExist() throws ConstraintViolationException, Exception {	
		Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(null);
		Mockito.doNothing().when(roleRepository).addRole(Mockito.any(Role.class));
		try {
			Boolean b = roleService.addRole(role);
			
			assertTrue(b);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).addRole(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test
	public void addNewRoleWhenRoleIsValidAndAlreadyExist() throws ConstraintViolationException, Exception {	
		Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(role);
		try {
			Boolean b = roleService.addRole(role);
			
			assertFalse(b);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	/**
	 * Update Role Tests: Should cover throwing a new Exception.
	 * 
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateRoleWhenRoleIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		roleService.updateRole(null);
	}
	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateRoleWhenRoleIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update role ... ", null, null);	
//		Mockito.doThrow(cve).when(roleRepository).updateRole(Mockito.any(Role.class));
//
//		role.setRole(null);
//		roleService.updateRole(role);
//		
//		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).updateRole(Mockito.any(Role.class));
//		Mockito.verifyNoMoreInteractions(roleRepository);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateRoleWhenUserIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update role ... ", null, null);	
//		Mockito.doThrow(cve).when(roleRepository).updateRole(Mockito.any(Role.class));
//		
//		role.setUser(null);
//		roleService.updateRole(role);
//		
//		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).updateRole(Mockito.any(Role.class));
//		Mockito.verifyNoMoreInteractions(roleRepository);
//	}
	
	/**
	 * Update Role Tests: Update Role is successful.
	 * @throws ConstraintViolationException 
	 * @throws Exception 
	 */
	
	@Test
	public void updateRoleWhenRoleIsValidAndAlreadyExists() throws ConstraintViolationException, Exception {	
		Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(role);
		Mockito.doNothing().when(roleRepository).updateRole(Mockito.any(Role.class));
		try {
			Boolean b = roleService.updateRole(role);
			
			assertTrue(b);
		} catch (Exception e) {
			fail("User should update successfully");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).updateRole(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test
	public void updateRoleWhenRoleIsValidAndDoesNotAlreadyExist() throws ConstraintViolationException, Exception {	
		Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(null);
		try {
			Boolean b = roleService.updateRole(role);
			
			assertFalse(b);
		} catch (Exception e) {
			fail("User should update successfully");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	/**
	 * Delete Role Tests: Should cover throwing a new Exception.
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void deleteRoleWhenRoleIsNullShouldThrowException() throws Exception {
		roleService.deleteRole(null);
	}
	
	@Test
	public void deleteRoleWhenRoleIsValidAndRoleIsDeleted() throws Exception {
		Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(role).thenReturn(null);
		Mockito.doNothing().when(roleRepository).deleteRole(Mockito.any(Role.class));
		try {
			Boolean b = roleService.deleteRole(role);
			
			assertTrue(b);
		} catch (Exception e) {
			fail("Role should add successfully");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(2)).findById(Mockito.anyLong());
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).deleteRole(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test
	public void deleteRoleWhenRoleIsValidAndRoleDoesNotExist() throws Exception {
		Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(null);
		try {
			Boolean b = roleService.deleteRole(role);
			
			assertFalse(b);
		} catch (Exception e) {
			fail("Role should add successfully");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test
	public void deleteRoleWhenRoleIsValidAndDoesNotDelete() throws Exception {
		Mockito.when(roleRepository.findById(Mockito.anyLong())).thenReturn(role).thenReturn(role);
		Mockito.doNothing().when(roleRepository).deleteRole(Mockito.any(Role.class));
		try {
			Boolean b = roleService.deleteRole(role);
			
			assertFalse(b);
		} catch (Exception e) {
			fail("Role should add successfully");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(2)).findById(Mockito.anyLong());
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).deleteRole(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test(expected = RuntimeException.class)
	public void findRoleByIdThrowsRuntimeException() throws RuntimeException {		
		Mockito.doThrow(new RuntimeException()).when(roleRepository).findById(Mockito.anyLong());
		
		roleService.findById(1L);
		
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test(expected = HibernateException.class)
	public void findRoleByIdThrowsHibernateException() throws HibernateException {		
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(roleRepository).findById(Mockito.anyLong());
		
		roleService.findById(1L);
		
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test
	public void findRoleByIdIsSuccessful() {
		role.setId(1L);		
		Mockito.doReturn(role).when(roleRepository).findById(Mockito.anyLong());	
		try {
			Role r = roleService.findById(1L);
			
			assertNotNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test
	public void findRoleByIdFails(){		
		Mockito.doReturn(null).when(roleRepository).findById(Mockito.anyLong());	
		try {
			Role r = roleService.findById(1L);
			
			assertNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test(expected = RuntimeException.class)
	public void findByRoleThrowsRuntimeException() throws RuntimeException {	
		Mockito.doThrow(new RuntimeException()).when(roleRepository).findByRole(Mockito.anyLong());
		
		roleService.findByRole(1L);
		
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findByRole(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test(expected = HibernateException.class)
	public void findByRoleThrowsHibernateException() throws HibernateException {		
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(roleRepository).findByRole(Mockito.anyLong());
		
		roleService.findByRole(1L);
		
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findByRole(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test
	public void findByRoleIsSuccessful() {
		List<Role> lst = new ArrayList<Role>();
		lst.add(role);
		
		Mockito.when(roleRepository.findByRole(Mockito.anyLong())).thenReturn(lst);	
		try {
			List<Role> r = roleService.findByRole(1L);
			
			assertNotNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findByRole(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test
	public void findByRoleFails(){
		Mockito.when(roleRepository.findByRole(Mockito.anyLong())).thenReturn(null);
		try {
			List<Role> r = roleService.findByRole(1L);
			
			assertNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findByRole(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test(expected = RuntimeException.class)
	public void findAllRolesThrowsRuntimeException() throws RuntimeException {
		Mockito.doThrow(new RuntimeException()).when(roleRepository).findAllRoles();
		
		roleService.findAllRoles();
		
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findAllRoles();
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test(expected = HibernateException.class)
	public void findAllRolesThrowsHibernateException() throws HibernateException {
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(roleRepository).findAllRoles();
		
		roleService.findAllRoles();
		
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findAllRoles();
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test
	public void findAllRolesIsSuccessful() {
		List<Role> lst = new ArrayList<Role>();
		lst.add(role);
		
		Mockito.when(roleRepository.findAllRoles()).thenReturn(lst);
		try {
			List<Role> r = roleService.findAllRoles();
			
			assertNotNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findAllRoles();
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
	
	@Test
	public void findAllRolesFails(){
		Mockito.when(roleRepository.findAllRoles()).thenReturn(null);		
		try {
			List<Role> r = roleService.findAllRoles();
			
			assertNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(roleRepository, VerificationModeFactory.times(1)).findAllRoles();
		Mockito.verifyNoMoreInteractions(roleRepository);
	}
}
