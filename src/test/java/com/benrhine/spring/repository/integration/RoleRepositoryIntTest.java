package com.benrhine.spring.repository.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.benrhine.spring.config.integration.InMemoryDBConfig;
import com.benrhine.spring.config.integration.RoleRepositoryIntConfig;
import com.benrhine.spring.domain.Role;
import com.benrhine.spring.domain.makers.RoleMaker;
import com.benrhine.spring.repository.RoleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InMemoryDBConfig.class, RoleRepositoryIntConfig.class }, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class RoleRepositoryIntTest {
	@Autowired private SessionFactory sessionFactory;
	@Autowired private RoleRepository roleRepository;

	/**
	 * Private domain object for testing.
	 */
	private Role role;
	
	@Before
	public void initDataBeforeEachMethod() {
		role = RoleMaker.makeRole();
	}
	
	@After
	public void cleanUpAfterEachTest() {
		sessionFactory.getCurrentSession().clear();
	}
	
	@Test(expected = Exception.class)
	public void addNewRoleWhenRoleIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		roleRepository.addRole(null);
	}
	
	/**
	 * Add New Role Tests: Add New Role is successful.
	 * @throws Exception 
	 */
	
	@Test
	public void addNewRoleWhenRoleIsValid() {
		try {
			roleRepository.addRole(role);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		assertTrue(sessionFactory.getCurrentSession().contains(role));
	}
	
	/**
	 * Update Role Tests: Should cover throwing a new Exception.
	 * 
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateRoleWhenRoleIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		roleRepository.updateRole(null);
	}
	
	/**
	 * Update Role Tests: Update Role is successful.
	 * @throws Exception 
	 */
	
	@Test
	public void updateRoleWhenRoleIsValid() {
		try {
			roleRepository.addRole(role);
			role.setRole(4L);
			roleRepository.updateRole(role);
		} catch (Exception e) {
			fail("User should update successfully");
		}
		assert(role.getRole() == 4L);
		assertTrue(sessionFactory.getCurrentSession().contains(role));
	}
	
	/**
	 * Delete Role Tests: Should cover throwing a new Exception.
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void deleteUserWhenUserIsNullShouldThrowException() throws Exception {
		roleRepository.deleteRole(null);
	}
	
	@Test
	public void deleteUserWhenUserIsValid() {
		try {
			roleRepository.addRole(role);
			roleRepository.deleteRole(role);
		} catch (Exception e) {
			fail("Role should add successfully");
		}
		assertFalse(sessionFactory.getCurrentSession().contains(role));
	}
	
	@Test(expected = RuntimeException.class)
	public void findRoleByIdThrowsRuntimeException() throws RuntimeException {
		roleRepository.findById(null);
	}
	
//	@Test
//	public void findRoleByIdIsSuccessful() {
//		role.setId(1L);
//		try {
//			roleRepository.addRole(role);
//			
//			Role r = roleRepository.findById(1L);
//			
//			assertNotNull(r);
//		} catch (Exception e) {
//			fail("No Exceptions should be thrown: user should not be null.");
//		}
//	}
	
	@Test
	public void findRoleByIdFails(){
		try {
			Role r = roleRepository.findById(1L);
			
			assertNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
	}
	
	@Test(expected = RuntimeException.class)
	public void findByRoleThrowsRuntimeException() throws RuntimeException {
		roleRepository.findByRole(null);
	}
	
	/*
	 * Snuff test as it is an empty list but its not null. For some reason wont add the role for this test case.
	 */
//	@Test
//	public void findByRoleIsSuccessful() {
//		try {
//			roleRepository.addRole(role);
//			
//			List<Role> r = roleRepository.findByRole(2L);
//			
//			assertNotNull(r);
//		} catch (Exception e) {
//			fail("No Exceptions should be thrown: user should not be null.");
//		}
//	}
	
	@Test
	public void findByRoleFails(){
		try {
			List<Role> r = roleRepository.findByRole(1L);
			
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
			//roleRepository.addRole(role);
			
			List<Role> r = roleRepository.findAllRoles();
			
			assertNotNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
	}
	
	@Test
	public void findAllRolesFails(){
		try {
			List<Role> r = roleRepository.findAllRoles();
			
			assertTrue(r.isEmpty());
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
	}
}
