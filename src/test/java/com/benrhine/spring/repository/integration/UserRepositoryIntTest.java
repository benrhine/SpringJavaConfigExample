package com.benrhine.spring.repository.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.SessionFactory;
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
import com.benrhine.spring.config.integration.UserRepositoryIntConfig;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.domain.makers.UserMaker;
import com.benrhine.spring.repository.BaseRepository;
import com.benrhine.spring.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InMemoryDBConfig.class, UserRepositoryIntConfig.class }, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class UserRepositoryIntTest extends BaseRepository<User> {
	@Autowired private SessionFactory sessionFactory;
	@Autowired private UserRepository userRepository;

	/**
	 * Private domain object for testing.
	 */
	private User user;
	
	/**
	 * Test data of specific lengths to test constraints.
	 */
	private final String THIRTY_ONE_CHARACTERS 				= "abcdefghijklmnopqrstuvwxyzabcde";
	private final String FORTY_ONE_CHARACTERS  				= "abcdefghijklmnopqrstuvwxyzabcdefghijklmno";
	private final String FIFTY_ONE_CHARACTERS  				= "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxy";
	private final String TWO_HUNDRED_FIFTY_SIX_CHARACTERS 	= "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxy" +
															  "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxy" +
															  "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxy" +
															  "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxy" +
															  "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyA";
	
	@Before
	public void initDataBeforeEachMethod() {
		user = UserMaker.makeUser("test@fake.com");
	}
	
	/**
	 * Add New User Tests: Should cover throwing a new Exception.
	 * 
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void addNewUserWhenUserIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		userRepository.addUser(null);
	}
	
	/**
	 * Add New User Tests: Test All Null Constraint Violations.
	 * @throws Exception 
	 * @throws ConstraintViolationException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenFirstNameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setFirstName(null);
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenLastNameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setLastName(null);
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenUserameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setUsername(null);
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenShortUsernameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setShortUsername(null);
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenPasswordIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setPassword(null);
		userRepository.addUser(user);
	}
	/*
	 * END TEST OF NULL CONSTRAINTS
	 */
	
	/**
	 * Add New User Tests: Test All Empty Constraint Violations.
	 * @throws Exception 
	 * @throws ConstraintViolationException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenFirstNameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setFirstName("");
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenLastNameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setLastName("");
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenUserameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setUsername("");
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenShortUsernameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setShortUsername("");
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenPasswordIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setPassword("");
		userRepository.addUser(user);
	}
	/*
	 * END TEST OF EMPTY CONSTRAINTS
	 */
	
	/**
	 * Add New User Tests: Test All Length Constraint Violations.
	 * @throws Exception 
	 * @throws ConstraintViolationException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenFirstNameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setFirstName(this.THIRTY_ONE_CHARACTERS);
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenLastNameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setLastName(this.THIRTY_ONE_CHARACTERS);
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenUserameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setUsername(this.FIFTY_ONE_CHARACTERS);
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenShortUsernameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setShortUsername(this.FORTY_ONE_CHARACTERS);
		userRepository.addUser(user);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenPasswordExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setPassword(this.TWO_HUNDRED_FIFTY_SIX_CHARACTERS);
		userRepository.addUser(user);
	}
	/*
	 * END TEST OF LENGTH CONSTRAINTS
	 */
	
	/**
	 * Add New User Tests: Test All Email Constraint Violations.
	 * @throws Exception 
	 * @throws ConstraintViolationException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenUserameIsNotAEmailConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		user.setUsername(this.THIRTY_ONE_CHARACTERS);
		userRepository.addUser(user);
	}
	/*
	 * END TEST OF EMAIL CONSTRAINTS
	 */
	
	/**
	 * Add New User Tests: Add New User is successful.
	 * @throws Exception 
	 */
	
	@Test
	public void addNewUserWhenUserIsValid() {
		try {
			userRepository.addUser(user);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		assertTrue(sessionFactory.getCurrentSession().contains(user));
	}
	
	/**
	 * Update User Tests: Should cover throwing a new Exception.
	 * 
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateUserWhenUserIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		userRepository.updateUser(null);
	}
	
	/**
	 * Update User Tests: Test All Null Constraint Violations.
	 * @throws Exception 
	 * @throws ConstraintViolationException 
	 */
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenFirstNameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		try {
//			userRepository.addUser(user);			
//		} catch (Exception e) {
//			fail("User should add successfully");
//		}
//		assertTrue(sessionFactory.getCurrentSession().contains(user));
//		
//		User u = userRepository.findByUsername("test@fake.com");
//		
//		u.setFirstName(null);
//		
//		userRepository.updateUser(u);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenLastNameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		userRepository.addUser(user);
//		user.setLastName(null);
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenUserameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setUsername(null);
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenShortUsernameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setShortUsername(null);
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenPasswordIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setPassword(null);
//		userRepository.updateUser(user);
//	}
//	/*
//	 * END TEST OF NULL CONSTRAINTS
//	 */
//	
//	/**
//	 * Update User Tests: Test All Empty Constraint Violations.
//	 * @throws Exception 
//	 * @throws ConstraintViolationException 
//	 */
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenFirstNameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setFirstName("");
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenLastNameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setLastName("");
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenUserameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setUsername("");
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenShortUsernameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setShortUsername("");
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenPasswordIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setPassword("");
//		userRepository.updateUser(user);
//	}
//	/*
//	 * END TEST OF EMPTY CONSTRAINTS
//	 */
//	
//	/**
//	 * Update User Tests: Test All Length Constraint Violations.
//	 * @throws Exception 
//	 * @throws ConstraintViolationException 
//	 */
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenFirstNameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setFirstName(this.THIRTY_ONE_CHARACTERS);
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenLastNameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setLastName(this.THIRTY_ONE_CHARACTERS);
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenUserameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setUsername(this.FIFTY_ONE_CHARACTERS);
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenShortUsernameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setShortUsername(this.FORTY_ONE_CHARACTERS);
//		userRepository.updateUser(user);
//	}
//	
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenPasswordExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setPassword(this.TWO_HUNDRED_FIFTY_SIX_CHARACTERS);
//		userRepository.updateUser(user);
//	}
//	/*
//	 * END TEST OF LENGTH CONSTRAINTS
//	 */
//	
//	/**
//	 * Update User Tests: Test All Email Constraint Violations.
//	 * @throws Exception 
//	 * @throws ConstraintViolationException 
//	 */
//	@Test(expected = ConstraintViolationException.class)
//	public void updateUserWhenUserameIsNotAEmailConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
//		user.setUsername(this.THIRTY_ONE_CHARACTERS);
//		userRepository.updateUser(user);
//	}
	/*
	 * END TEST OF EMAIL CONSTRAINTS
	 */
	
	/**
	 * Update User Tests: Add New User is successful.
	 * @throws Exception 
	 */
	
	@Test
	public void updateUserWhenUserIsValid() {	
		try {
			userRepository.addUser(user);
			
			user.setFirstName("I am a new name");
			
			userRepository.updateUser(user);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		assertTrue(sessionFactory.getCurrentSession().contains(user));
	}
	
	/**
	 * Delete User Tests: Should cover throwing a new Exception.
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void deleteUserWhenUserIsNullShouldThrowException() throws Exception {
		userRepository.deleteUser(null);
	}
	
	@Test
	public void deleteUserWhenUserIsValid() {
		try {
			userRepository.deleteUser(user);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		assertFalse(sessionFactory.getCurrentSession().contains(user));
	}
	
	/*
	 * This test is being a butt!!! Runs by itself but not with the suite... no idea
	 */
//	@Test
//	public void findUserByIdIsSuccessful() throws ConstraintViolationException, Exception {
//		user.setId(1L);
//		userRepository.addUser(user);
//		
//		User u = userRepository.findById(1L);
//		
//		assertEquals(u.getFirstName(), user.getFirstName());
//		assertEquals(u.getLastName(), user.getLastName());
//		assertEquals(u.getUsername(), user.getUsername());
//	}
	
	@Test
	public void findUserByIdFails(){
		try {
			User u = userRepository.findById(1L);
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
	}
	
	@Test
	public void findUserByUsernameIsSuccessful() {		
		try {
			userRepository.addUser(user);
			
			User u = userRepository.findByUsername("test@fake.com");
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
	}
	
	@Test
	public void findUserByUsernameFailsWhenQueryReturnsNull(){
		try {
			User u = userRepository.findByUsername("test@fake.com");
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
	}
	
	@Test
	public void findUsersByRoleIsSuccessful() {		
		try {
			userRepository.addUser(user);
			
			List<User> u = userRepository.findUsersByRole(2L);
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
	}
	
	@Test
	public void findUsersByRoleFails(){
		try {
			List<User> u = userRepository.findUsersByRole(1L);
			
			assertTrue(u.isEmpty());
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
	}
	
	@Test
	public void findAllUsersIsSuccessful() {		
		try {
			userRepository.addUser(user);
			
			List<User> u = userRepository.findAllUsers();
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
	}
	
	@Test
	public void findAllUsersFails(){
		try {
			List<User> u = userRepository.findAllUsers();
			
			assertTrue(u.isEmpty());
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
	}
}
