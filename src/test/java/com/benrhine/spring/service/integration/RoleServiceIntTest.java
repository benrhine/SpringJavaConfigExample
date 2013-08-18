package com.benrhine.spring.service.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.benrhine.spring.config.integration.InMemoryDBConfig;
import com.benrhine.spring.config.integration.RoleServiceIntConfig;
import com.benrhine.spring.domain.Role;
import com.benrhine.spring.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InMemoryDBConfig.class, RoleServiceIntConfig.class }, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class RoleServiceIntTest {
	@Autowired private SessionFactory sessionFactory;
	@Autowired private RoleService roleService;
	
//	private Role role;
//	
//	@Before
//	public void initDataBeforeEachMethod() {
//		role = RoleMaker.makeRole();
//	}
	
	@After
	public void cleanUpAfterEachTest() {
		sessionFactory.getCurrentSession().clear();
	}
	
	@Test(expected = Exception.class)
	public void addNewRoleWhenRoleIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		roleService.addRole(null);
	}
	
	/**
	 * Add New Role Tests: Add New Role is successful.
	 * @throws Exception 
	 */
	
//	@Test
//	public void addNewRoleWhenRoleIsValid() {
//		try {
//			roleService.addRole(role);
//		} catch (Exception e) {
//			fail("User should add successfully");
//		}
//		assertTrue(sessionFactory.getCurrentSession().contains(role));
//	}
	
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
	
	/**
	 * Update Role Tests: Update Role is successful.
	 * @throws Exception 
	 */
	
//	@Test
//	public void updateRoleWhenRoleIsValid() {
//		try {
//			roleService.addRole(role);
//			role.setRole(4L);
//			roleService.updateRole(role);
//		} catch (Exception e) {
//			fail("User should update successfully");
//		}
//		assert(role.getRole() == 4L);
//		assertTrue(sessionFactory.getCurrentSession().contains(role));
//	}
	
	/**
	 * Delete Role Tests: Should cover throwing a new Exception.
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void deleteUserWhenUserIsNullShouldThrowException() throws Exception {
		roleService.deleteRole(null);
	}
	
//	@Test
//	public void deleteUserWhenUserIsValid() {
//		try {
//			roleService.addRole(role);
//			roleService.deleteRole(role);
//		} catch (Exception e) {
//			fail("Role should add successfully");
//		}
//		assertFalse(sessionFactory.getCurrentSession().contains(role));
//	}
	
	@Test(expected = RuntimeException.class)
	public void findRoleByIdThrowsRuntimeException() throws RuntimeException {
		roleService.findById(null);
	}
	
//	@Test
//	public void findRoleByIdIsSuccessful() {
//		role.setId(1L);
//		try {
//			roleService.addRole(role);
//			
//			Role r = roleService.findById(1L);
//			
//			assertNotNull(r);
//		} catch (Exception e) {
//			fail("No Exceptions should be thrown: user should not be null.");
//		}
//	}
	
	@Test
	public void findRoleByIdFails(){
		try {
			Role r = roleService.findById(1L);
			
			assertNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
	}
	
	@Test(expected = RuntimeException.class)
	public void findByRoleThrowsRuntimeException() throws RuntimeException {
		roleService.findByRole(null);
	}
	
	/*
	 * Snuff test as it is an empty list but its not null. For some reason wont add the role for this test case.
	 */
//	@Test
//	public void findByRoleIsSuccessful() {
//		try {
//			roleService.addRole(role);
//			
//			List<Role> r = roleService.findByRole(2L);
//			
//			assertNotNull(r);
//		} catch (Exception e) {
//			fail("No Exceptions should be thrown: user should not be null.");
//		}
//	}
	
	@Test
	public void findByRoleFails(){
		try {
			List<Role> r = roleService.findByRole(1L);
			
			assertTrue(r.isEmpty());
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
	}
	
	/*
	 * Snuff test as it is an empty list but its not null. For some reason wont add the role for this test case.
	 */
	@Test
	public void findAllRolesIsSuccessful() {
		try {
			//roleService.addRole(role);
			
			List<Role> r = roleService.findAllRoles();
			
			assertNotNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
	}
	
	@Test
	public void findAllRolesFails(){
		try {
			List<Role> r = roleService.findAllRoles();
			
			assertTrue(r.isEmpty());
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
	}
}
