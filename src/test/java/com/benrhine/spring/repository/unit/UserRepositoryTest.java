package com.benrhine.spring.repository.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

import com.benrhine.spring.domain.User;
import com.benrhine.spring.domain.makers.UserMaker;
import com.benrhine.spring.repository.UserRepository;
import com.benrhine.spring.repository.UserRepositoryImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class UserRepositoryTest {
	/**
	 * @Mock annotation is used as opposed to importing configuration classes
	 * with the @ContextConfiguration(classes = { TestConfig.class }) as it is
	 * much cleaner and does the same thing.
	 */
	@Mock private Query query;
	@Mock private Session session;
	@Mock private SessionFactory sessionFactory;
	
	/**
	 * Use @InjectMocks to dynamically insert your mocked objects into the
	 * "LIVE" object you are testing.
	 */
	@InjectMocks private static UserRepository userRepository;
	
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
		userRepository = new UserRepositoryImpl();
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
		
		user = UserMaker.makeUser("test@fake.com");
	}
	
	/**
	 * Tear down that occurs after every single test. Resets mocked
	 * objects to their original state.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDownAfterEveryMethod() throws Exception {
		Mockito.reset(session);
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
	 */
	@Test
	public void addNewUserWhenFirstNameIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setFirstName(null);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenLastNameIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setLastName(null);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenUserameIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setUsername(null);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenShortUsernameIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setShortUsername(null);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenPasswordIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setPassword(null);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	/*
	 * END TEST OF NULL CONSTRAINTS
	 */
	
	/**
	 * Add New User Tests: Test All Empty Constraint Violations.
	 */
	@Test
	public void addNewUserWhenFirstNameIsEmptyConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setFirstName("");
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenLastNameIsEmptyConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setLastName("");
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenUserameIsEmptyConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setUsername("");
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenShortUsernameIsEmptyConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setShortUsername("");
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenPasswordIsEmptyConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setPassword("");
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	/*
	 * END TEST OF EMPTY CONSTRAINTS
	 */
	
	/**
	 * Add New User Tests: Test All Length Constraint Violations.
	 */
	@Test
	public void addNewUserWhenFirstNameExceedsLengthConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setFirstName(this.THIRTY_ONE_CHARACTERS);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenLastNameExceedsLengthConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setLastName(this.THIRTY_ONE_CHARACTERS);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenUserameExceedsLengthConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setUsername(this.FIFTY_ONE_CHARACTERS);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenShortUsernameExceedsLengthConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setShortUsername(this.FORTY_ONE_CHARACTERS);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewUserWhenPasswordExceedsLengthConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setPassword(this.TWO_HUNDRED_FIFTY_SIX_CHARACTERS);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	/*
	 * END TEST OF LENGTH CONSTRAINTS
	 */
	
	/**
	 * Add New User Tests: Test All Email Constraint Violations.
	 */
	@Test
	public void addNewUserWhenUserameIsNotAEmailConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(user);
		try {
			user.setUsername(this.THIRTY_ONE_CHARACTERS);
			userRepository.addUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
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
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.save(user)).thenReturn(true);
		try {
			userRepository.addUser(user);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
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
	 */
	@Test
	public void updateUserWhenFirstNameIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setFirstName(null);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenLastNameIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setLastName(null);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenUserameIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setUsername(null);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenShortUsernameIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setShortUsername(null);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenPasswordIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setPassword(null);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	/*
	 * END TEST OF NULL CONSTRAINTS
	 */
	
	/**
	 * Update User Tests: Test All Empty Constraint Violations.
	 */
	@Test
	public void updateUserWhenFirstNameIsEmptyConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setFirstName("");
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenLastNameIsEmptyConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setLastName("");
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenUserameIsEmptyConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setUsername("");
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenShortUsernameIsEmptyConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setShortUsername("");
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenPasswordIsEmptyConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setPassword("");
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	/*
	 * END TEST OF EMPTY CONSTRAINTS
	 */
	
	/**
	 * Update User Tests: Test All Length Constraint Violations.
	 */
	@Test
	public void updateUserWhenFirstNameExceedsLengthConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setFirstName(this.THIRTY_ONE_CHARACTERS);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenLastNameExceedsLengthConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setLastName(this.THIRTY_ONE_CHARACTERS);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenUserameExceedsLengthConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setUsername(this.FIFTY_ONE_CHARACTERS);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenShortUsernameExceedsLengthConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setShortUsername(this.FORTY_ONE_CHARACTERS);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateUserWhenPasswordExceedsLengthConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setPassword(this.TWO_HUNDRED_FIFTY_SIX_CHARACTERS);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	/*
	 * END TEST OF LENGTH CONSTRAINTS
	 */
	
	/**
	 * Update User Tests: Test All Email Constraint Violations.
	 */
	@Test
	public void updateUserWhenUserameIsNotAEmailConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update user ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(user);
		try {
			user.setUsername(this.THIRTY_ONE_CHARACTERS);
			userRepository.updateUser(user);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update user ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	/*
	 * END TEST OF EMAIL CONSTRAINTS
	 */
	
	/**
	 * Update User Tests: Add New User is successful.
	 * @throws Exception 
	 */
	
	@Test
	public void updateUserWhenUserIsValid() {	
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doNothing().when(session).update(user);
		try {
			userRepository.updateUser(user);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
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
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doNothing().when(session).delete(user);
		try {
			userRepository.deleteUser(user);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).delete(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = RuntimeException.class)
	public void findUserByIdThrowsRuntimeException() throws RuntimeException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new RuntimeException()).when(session).get((Class<User>) Mockito.anyObject(), Mockito.anyLong());
		
		userRepository.findById(1L);
		
		Mockito.verify(session, VerificationModeFactory.times(1)).get((Class<User>) Mockito.anyObject(), Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = HibernateException.class)
	public void findUserByIdThrowsHibernateException() throws HibernateException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(session).get((Class<User>) Mockito.anyObject(), Mockito.anyLong());
		
		userRepository.findById(1L);
		
		Mockito.verify(session, VerificationModeFactory.times(1)).get((Class<User>) Mockito.anyObject(), Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findUserByIdIsSuccessful() {
		user.setId(1L);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doReturn(user).when(session).get((Class<User>) Mockito.anyObject(), Mockito.anyLong());
		
		try {
			User u = userRepository.findById(1L);
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).get((Class<User>) Mockito.anyObject(), Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findUserByIdFails(){
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doReturn(null).when(session).get((Class<User>) Mockito.anyObject(), Mockito.anyLong());
		
		try {
			User u = userRepository.findById(1L);
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).get((Class<User>) Mockito.anyObject(), Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test(expected = RuntimeException.class)
	public void findUserByUsernameThrowsRuntimeException() throws RuntimeException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new RuntimeException()).when(session).createQuery(Mockito.anyString());
		
		userRepository.findByUsername("test@fake.com");
		
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test(expected = HibernateException.class)
	public void findUserByUsernameThrowsHibernateException() throws HibernateException {
		List<User> lst = new ArrayList<User>();
		lst.add(user);
		
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(session).createQuery(Mockito.anyString());
		
		userRepository.findByUsername("test@fake.com");
		
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findUserByUsernameIsSuccessful() {
		List<User> lst = new ArrayList<User>();
		lst.add(user);
		
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(lst);
		
		try {
			User u = userRepository.findByUsername("test@fake.com");
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findUserByUsernameFailsWhenQueryReturnsNull(){
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(null);
		
		try {
			User u = userRepository.findByUsername("test@fake.com");
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findUserByUsernameFailsWhenQueryReturnsMultipleResults(){
		List<User> lst = new ArrayList<User>();
		user.setId(1L);
		lst.add(user);
		user.setId(2L);
		lst.add(user);
		assertEquals(lst.size(), 2);
		
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(lst);
		
		try {
			User u = userRepository.findByUsername("test@fake.com");
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test(expected = RuntimeException.class)
	public void findUsersByRoleThrowsRuntimeException() throws RuntimeException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new RuntimeException()).when(session).createQuery(Mockito.anyString());
		
		userRepository.findUsersByRole(1L);
		
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test(expected = HibernateException.class)
	public void findUsersByRoleThrowsHibernateException() throws HibernateException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(session).createQuery(Mockito.anyString());
		
		userRepository.findUsersByRole(1L);
		
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findUsersByRoleIsSuccessful() {
		List<User> lst = new ArrayList<User>();
		lst.add(user);
		
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(lst);
		
		try {
			List<User> u = userRepository.findUsersByRole(1L);
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findUsersByRoleFails(){
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(null);
		
		try {
			List<User> u = userRepository.findUsersByRole(1L);
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test(expected = RuntimeException.class)
	public void findAllUsersThrowsRuntimeException() throws RuntimeException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new RuntimeException()).when(session).createQuery(Mockito.anyString());
		
		userRepository.findAllUsers();
		
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test(expected = HibernateException.class)
	public void findAllUsersThrowsHibernateException() throws HibernateException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(session).createQuery(Mockito.anyString());
		
		userRepository.findAllUsers();
		
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findAllUsersIsSuccessful() {
		List<User> lst = new ArrayList<User>();
		lst.add(user);
		
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(lst);
		
		try {
			List<User> u = userRepository.findAllUsers();
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findAllUsersFails(){
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(null);
		
		try {
			List<User> u = userRepository.findAllUsers();
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}	
}
