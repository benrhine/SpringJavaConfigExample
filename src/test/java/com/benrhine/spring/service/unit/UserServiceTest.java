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

import com.benrhine.spring.domain.User;
import com.benrhine.spring.domain.makers.UserMaker;
import com.benrhine.spring.repository.UserRepository;
import com.benrhine.spring.service.UserService;
import com.benrhine.spring.service.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class UserServiceTest {
	/**
	 * @Mock annotation is used as opposed to importing configuration classes
	 * with the @ContextConfiguration(classes = { TestConfig.class }) as it is
	 * much cleaner and does the same thing.
	 */
	@Mock UserRepository userRepository;
	
	/**
	 * Use @InjectMocks to dynamically insert your mocked objects into the
	 * "LIVE" object you are testing.
	 */
	@InjectMocks private static UserService userService;
	
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
		userService = new UserServiceImpl();
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
		Mockito.reset(userRepository);
	}
	
	@Test(expected = Exception.class)
	public void addNewUserWhenUserIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		userService.addUser(null);
	}
	
	/**
	 * Add New User Tests: Test All Null Constraint Violations.
	 * @throws Exception 
	 * @throws ConstraintViolationException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenFirstNameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setFirstName(null);
		userService.addUser(user);
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenLastNameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setLastName(null);
		userService.addUser(user);
			
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenUserameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setUsername(null);
		userService.addUser(user);
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenShortUsernameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setShortUsername(null);
		userService.addUser(user);
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenPasswordIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setPassword(null);
		userService.addUser(user);
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
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
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setFirstName("");
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenLastNameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setLastName("");
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenUserameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setUsername("");
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenShortUsernameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setShortUsername("");
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenPasswordIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setPassword("");
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	/*
	 * END TEST OF EMPTY CONSTRAINTS
	 */
	
	/**
	 * Add New User Tests: Test All Length Constraint Violations.
	 * @throws Exception 
	 * @throws javax.validation.ConstraintViolationException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenFirstNameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setFirstName(this.THIRTY_ONE_CHARACTERS);
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenLastNameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setLastName(this.THIRTY_ONE_CHARACTERS);
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenUserameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setUsername(this.FIFTY_ONE_CHARACTERS);
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenShortUsernameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setShortUsername(this.FORTY_ONE_CHARACTERS);
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void addNewUserWhenPasswordExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setPassword(this.TWO_HUNDRED_FIFTY_SIX_CHARACTERS);
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
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
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.doThrow(cve).when(userRepository).addUser(user);
		
		user.setUsername(this.THIRTY_ONE_CHARACTERS);
		userService.addUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	/*
	 * END TEST OF EMAIL CONSTRAINTS
	 */
	
	@Test
	public void addNewUserWhenUserIsValid() throws ConstraintViolationException, Exception {
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);
		Mockito.doNothing().when(userRepository).addUser(user);
		try {
			final Boolean b = userService.addUser(user);
			
			assertTrue(b);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).addUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void addNewUserFailsWhenUserAlreadyExists() throws ConstraintViolationException, Exception {	
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		try {
			final Boolean b = userService.addUser(user);
			
			assertFalse(b);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	/**
	 * Update User Tests: Should cover throwing a new Exception.
	 * 
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateUserWhenUserIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		userService.updateUser(null);
	}
	
	/**
	 * Update User Tests: Test All Null Constraint Violations.
	 * @throws Exception 
	 * @throws ConstraintViolationException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenFirstNameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setFirstName(null);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenLastNameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setLastName(null);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenUserameIsNullConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setUsername(null);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenShortUsernameIsNullConstraintShouldThrowConstraintViolation() throws javax.validation.ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setShortUsername(null);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenPasswordIsNullConstraintShouldThrowConstraintViolation() throws javax.validation.ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setPassword(null);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	/*
	 * END TEST OF NULL CONSTRAINTS
	 */
	
	/**
	 * Update User Tests: Test All Empty Constraint Violations.
	 * @throws Exception 
	 * @throws ConstraintViolationException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenFirstNameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setFirstName("");
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenLastNameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setLastName("");
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenUserameIsEmptyConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setUsername("");
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenShortUsernameIsEmptyConstraintShouldThrowConstraintViolation() throws javax.validation.ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setShortUsername("");
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenPasswordIsEmptyConstraintShouldThrowConstraintViolation() throws javax.validation.ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setPassword("");
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	/*
	 * END TEST OF EMPTY CONSTRAINTS
	 */
	
	/**
	 * Update User Tests: Test All Length Constraint Violations.
	 * @throws Exception 
	 * @throws ConstraintViolationException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenFirstNameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setFirstName(this.THIRTY_ONE_CHARACTERS);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenLastNameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setLastName(this.THIRTY_ONE_CHARACTERS);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenUserameExceedsLengthConstraintShouldThrowConstraintViolation() throws javax.validation.ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setUsername(this.FIFTY_ONE_CHARACTERS);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenShortUsernameExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setShortUsername(this.FORTY_ONE_CHARACTERS);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenPasswordExceedsLengthConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setPassword(this.TWO_HUNDRED_FIFTY_SIX_CHARACTERS);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	/*
	 * END TEST OF LENGTH CONSTRAINTS
	 */
	
	/**
	 * Update User Tests: Test All Email Constraint Violations.
	 * @throws Exception 
	 * @throws ConstraintViolationException 
	 */
	@Test(expected = ConstraintViolationException.class)
	public void updateUserWhenUserameIsNotAEmailConstraintShouldThrowConstraintViolation() throws ConstraintViolationException, Exception {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new user ... ", null);
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doThrow(cve).when(userRepository).updateUser(user);
		
		user.setUsername(this.THIRTY_ONE_CHARACTERS);
		userService.updateUser(user);
	
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	/*
	 * END TEST OF EMAIL CONSTRAINTS
	 */
	
	/**
	 * Update User Tests: Add New User is successful.
	 * @throws ConstraintViolationException 
	 * @throws Exception 
	 */
	
	@Test
	public void updateUserWhenUserIsValid() throws ConstraintViolationException, Exception {
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doNothing().when(userRepository).updateUser(user);
		try {
			final Boolean b = userService.updateUser(user);
			
			assertTrue(b);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).updateUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void updateUserFailsWhenUserDoesntExists() throws ConstraintViolationException, Exception {	
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);
		try {
			final Boolean b = userService.updateUser(user);
			
			assertFalse(b);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	/**
	 * Delete User Tests: Should cover throwing a new Exception.
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void deleteUserWhenUserIsNullShouldThrowException() throws Exception {
		userService.deleteUser(null);
	}
	
	@Test
	public void deleteUserWhenUserIsValid() throws Exception {
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doNothing().when(userRepository).deleteUser(user);
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(null);
		try {
			final Boolean b = userService.deleteUser(user);
			
			assertTrue(b);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).deleteUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void deleteUserWhenUserDoesNotExist() throws Exception {
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);
		try {
			final Boolean b = userService.deleteUser(user);
			
			assertFalse(b);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void deleteUserWhenUserExistsButFailsToActuallyDelete() throws Exception {
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		Mockito.doNothing().when(userRepository).deleteUser(user);
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(user);
		try {
			final Boolean b = userService.deleteUser(user);
			
			assertFalse(b);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).deleteUser(Mockito.any(User.class));
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = RuntimeException.class)
	public void findUserByIdThrowsRuntimeException() throws HibernateException, RuntimeException {
		Mockito.doThrow(new RuntimeException()).when(userRepository).findById(Mockito.anyLong());
		
		userService.findById(1L);
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = HibernateException.class)
	public void findUserByIdThrowsHibernateException() throws HibernateException, RuntimeException {
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(userRepository).findById(Mockito.anyLong());
		
		userService.findById(1L);
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void findUserByIdIsSuccessful() {
		user.setId(1L);
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(user);
		
		try {
			User u = userService.findById(1L);
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void findUserByIdFails(){
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(null);
		
		try {
			User u = userService.findById(1L);
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = RuntimeException.class)
	public void findUserByUsernameThrowsRuntimeException() throws RuntimeException {
		Mockito.doThrow(new RuntimeException()).when(userRepository).findByUsername(Mockito.anyString());
		
		userService.findByUsername("test@fake.com");
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = HibernateException.class)
	public void findUserByUsernameThrowsHibernateException() throws HibernateException {
		List<User> lst = new ArrayList<User>();
		lst.add(user);
		
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(userRepository).findByUsername(Mockito.anyString());
		
		userService.findByUsername("test@fake.com");
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void findUserByUsernameIsSuccessful() {
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
		
		try {
			User u = userService.findByUsername("test@fake.com");
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void findUserByUsernameFailsWhenReturnsNull(){
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);
		
		try {
			User u = userService.findByUsername("test@fake.com");
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findByUsername(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = RuntimeException.class)
	public void findUsersByRoleThrowsRuntimeException() throws RuntimeException {
		Mockito.doThrow(new RuntimeException()).when(userRepository).findUsersByRole(Mockito.anyLong());
		
		userService.findUsersByRole(1L);
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findUsersByRole(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = HibernateException.class)
	public void findUsersByRoleThrowsHibernateException() throws HibernateException {
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(userRepository).findUsersByRole(Mockito.anyLong());
		
		userService.findUsersByRole(1L);
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findUsersByRole(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void findUsersByRoleIsSuccessful() {
		List<User> lst = new ArrayList<User>();
		lst.add(user);
		
		Mockito.when(userRepository.findUsersByRole(Mockito.anyLong())).thenReturn(lst);
		
		try {
			List<User> u = userService.findUsersByRole(1L);
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findUsersByRole(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void findUsersByRoleFails(){
		Mockito.when(userRepository.findUsersByRole(Mockito.anyLong())).thenReturn(null);
		
		try {
			List<User> u = userService.findUsersByRole(1L);
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findUsersByRole(Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = RuntimeException.class)
	public void findAllUsersThrowsRuntimeException() throws RuntimeException {
		Mockito.doThrow(new RuntimeException()).when(userRepository).findAllUsers();
		
		userService.findAllUsers();
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findAllUsers();
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test(expected = HibernateException.class)
	public void findAllUsersThrowsHibernateException() throws HibernateException {
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(userRepository).findAllUsers();
		
		userService.findAllUsers();
		
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findAllUsers();
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void findAllUsersIsSuccessful() {
		List<User> lst = new ArrayList<User>();
		lst.add(user);
		
		Mockito.when(userRepository.findAllUsers()).thenReturn(lst);
		
		try {
			List<User> u = userService.findAllUsers();
			
			assertNotNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findAllUsers();
		Mockito.verifyNoMoreInteractions(userRepository);
	}
	
	@Test
	public void findAllUsersFails(){
		Mockito.when(userRepository.findAllUsers()).thenReturn(null);
		
		try {
			List<User> u = userService.findAllUsers();
			
			assertNull(u);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(userRepository, VerificationModeFactory.times(1)).findAllUsers();
		Mockito.verifyNoMoreInteractions(userRepository);
	}	
}
