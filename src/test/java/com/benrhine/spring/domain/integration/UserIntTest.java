package com.benrhine.spring.domain.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Before;
import org.junit.Test;

import com.benrhine.spring.domain.Role;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.domain.makers.RoleMaker;
import com.benrhine.spring.domain.makers.UserMaker;

public class UserIntTest extends BaseDomainIntTest {
	
	private User user;
	private Role role;
	
	@Before
	public void initDataBeforeEachMethod() {
		user = UserMaker.makeUser("test@fake.com");
		role = RoleMaker.makeRole();		
	}
	
	/*
	 * Simple test to ensure database is up and running and that we have actually
	 * persisted a object.
	 */
	@Test
	public void showThatUserAndRoleHasBeenPersistedSuccessfully() {
		try {
			persist(user);
			persist(role);
			
			assertTrue(contains(user));
			assertTrue(contains(role));
		} catch (Exception e) {
			fail("Should not have failed");
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @NotNull, @NotEmpty annotations.
	 * 
	 * Note: This test is for both annotations as a null value triggers both.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenFirstNameIsNull() {
		try {
			user.setFirstName(null);			
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(2, e.getConstraintViolations().size());
			Object[] violations = e.getConstraintViolations().toArray();
			
			for (Object violation:violations) {
				assertEquals("firstName", ((ConstraintViolation<?>) violation).getPropertyPath().toString());
				
				if (((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType().equals(NotEmpty.class)) {
					assertEquals(NotEmpty.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				} else {
					assertEquals(NotNull.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				}
			}
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @NotEmpty annotations.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenFirstNameIsEmptyString() {
		try {
			user.setFirstName("");			
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("firstName", violation.getPropertyPath().toString());
			assertEquals(NotEmpty.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @Size(max = 30) annotations.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenFirstNameExceedsThirtyCharacters() {
		try {
			user.setFirstName("aaaaabbbbbcccccdddddeeeeefffffg");	// length = 31	
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("firstName", violation.getPropertyPath().toString());
			assertEquals(Size.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for when all annotations are valid.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canPersistUserWhenFirstNameIsNotEmptyAndIsLessThanThirtyCharacters() {
		try {
			user.setFirstName("Bob");			
		
			persist(user);
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("firstName", violation.getPropertyPath().toString());
		}
		assertEquals(user.getFirstName(), "Bob");
		assertTrue(contains(user));
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @NotNull, @NotEmpty annotations.
	 * 
	 * Note: This test is for both annotations as a null value triggers both.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenLastNameIsNull() {
		try {
			user.setLastName(null);			
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(2, e.getConstraintViolations().size());
			Object[] violations = e.getConstraintViolations().toArray();
			
			for (Object violation:violations) {
				assertEquals("lastName", ((ConstraintViolation<?>) violation).getPropertyPath().toString());
				
				if (((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType().equals(NotEmpty.class)) {
					assertEquals(NotEmpty.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				} else {
					assertEquals(NotNull.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				}
			}
		}
	}

	/* -------------------------------------------------------------------------------------
	 * Test for @NotEmpty annotations.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenLastNameIsEmptyString() {
		try {
			user.setLastName("");			
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("lastName", violation.getPropertyPath().toString());
			assertEquals(NotEmpty.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @Size(max = 30) annotations.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenLastNameExceedsThirtyCharacters() {
		try {
			user.setLastName("aaaaabbbbbcccccdddddeeeeefffffg");	// length = 31	
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("lastName", violation.getPropertyPath().toString());
			assertEquals(Size.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for when all annotations are valid.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canPersistUserWhenLastNameIsNotEmptyAndIsLessThanThirtyCharacters() {
		try {
			user.setLastName("Newhart");			
		
			persist(user);
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("lastName", violation.getPropertyPath().toString());
		}
		assertEquals(user.getLastName(), "Newhart");
		assertTrue(contains(user));
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @NotNull, @NotEmpty annotations.
	 * 
	 * Note: This test is for both annotations as a null value triggers both.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenUsernameIsNull() {
		try {
			user.setUsername(null);			
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(2, e.getConstraintViolations().size());
			Object[] violations = e.getConstraintViolations().toArray();
			
			for (Object violation:violations) {
				assertEquals("userName", ((ConstraintViolation<?>) violation).getPropertyPath().toString());
				
				if (((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType().equals(NotEmpty.class)) {
					assertEquals(NotEmpty.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				} else {
					assertEquals(NotNull.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				}
			}
		}
	}

	/* -------------------------------------------------------------------------------------
	 * Test for @NotEmpty annotations.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenUsernameIsEmptyString() {
		try {
			user.setUsername("");			
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("userName", violation.getPropertyPath().toString());
			assertEquals(NotEmpty.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @Size(max = 50), @Email annotations.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenUsernameExceedsFiftyCharactersAndIsNotAValidEmail() {
		try {
			user.setUsername("aaaaabbbbbcccccdddddeeeeefffffggggghhhhhiiiiikkkkkl");	// length = 51	
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(2, e.getConstraintViolations().size());
			Object[] violations = e.getConstraintViolations().toArray();
			
			for (Object violation:violations) {
				assertEquals("userName", ((ConstraintViolation<?>) violation).getPropertyPath().toString());
				
				if (((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class)) {
					assertEquals(Size.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				} else {
					assertEquals(Email.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				}
			}
		}
	}
	
	@Test
	public void canNotPersistUserWhenUsernameExceedsFiftyCharactersAndIsAValidEmail() {
		try {
			user.setUsername("aaaaabbbbbcccccdddddeeeeefffffggggghhhhhiiiii@fake.com");	// length = 54	
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("userName", violation.getPropertyPath().toString());
			assertEquals(Size.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	
	@Test
	public void canNotPersistUserWhenUsernameIsLessThanFiftyCharactersAndIsNotAValidEmail() {
		try {
			user.setUsername("aaaaabbbbbccccc");	// length = 15	
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("userName", violation.getPropertyPath().toString());
			assertEquals(Email.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	/*
	 * END SIZE AND EMAIL ANNOTATION TESTS.
	 */
	
	/* -------------------------------------------------------------------------------------
	 * Test for when all annotations are valid.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canPersistUserWhenUsernameIsNotEmptyLessThanFiftyCharactersAndIsAValidEmail() {
		try {
			user.setUsername("aaaaabbbbbccccc@fake.com");	// length = 24	
		
			persist(user);
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("userName", violation.getPropertyPath().toString());
		}
		assertEquals(user.getUsername(), "aaaaabbbbbccccc@fake.com");
		assertTrue(contains(user));
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @NotNull, @NotEmpty annotations.
	 * 
	 * Note: This test is for both annotations as a null value triggers both.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenPasswordIsNull() {
		try {
			user.setPassword(null);
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(2, e.getConstraintViolations().size());
			Object[] violations = e.getConstraintViolations().toArray();
			
			for (Object violation:violations) {
				assertEquals("userPass", ((ConstraintViolation<?>) violation).getPropertyPath().toString());
				
				if (((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType().equals(NotEmpty.class)) {
					assertEquals(NotEmpty.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				} else {
					assertEquals(NotNull.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				}
			}
		}
	}

	/* -------------------------------------------------------------------------------------
	 * Test for @NotEmpty annotations.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenPasswordIsEmptyString() {
		try {
			user.setPassword("");			
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("userPass", violation.getPropertyPath().toString());
			assertEquals(NotEmpty.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @Size(max = 255) annotations.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenPasswordExceedsTwoHundredFiftyFiveCharacters() {
		try {
			user.setPassword("aaaaabbbbbcccccdddddeeeeefffffgaaaaabbbbbcccccdddddeeeeefffffgaaaaabbbbbcccccdddddeeeeefffffgaaaaabbbbbcccccdddddeeeeefffffgaaaaabbbbbcccccdddddeeeeefffffgaaaaabbbbbcccccdddddeeeeefffffgaaaaabbbbbcccccdddddeeeeefffffgaaaaabbbbbcccccdddddeeeeefffffgaaaaabbbbbcccccdddddeeeeefffffg");	// length = 31	
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("userPass", violation.getPropertyPath().toString());
			assertEquals(Size.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for when all annotations are valid.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canPersistUserWhenPasswordIsNotEmptyAndIsLessThanTwoHundredFiftyFiveCharacters() {
		try {
			user.setPassword("12345");			
		
			persist(user);
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("userPass", violation.getPropertyPath().toString());
		}
		assertEquals(user.getPassword(), "12345");
		assertTrue(contains(user));
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @NotNull, @NotEmpty annotations.
	 * 
	 * Note: This test is for both annotations as a null value triggers both.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenShortUsernameIsNull() {
		try {
			user.setShortUsername(null);		
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(2, e.getConstraintViolations().size());
			Object[] violations = e.getConstraintViolations().toArray();
			
			for (Object violation:violations) {
				assertEquals("shortUsername", ((ConstraintViolation<?>) violation).getPropertyPath().toString());
				
				if (((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType().equals(NotEmpty.class)) {
					assertEquals(NotEmpty.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				} else {
					assertEquals(NotNull.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
				}
			}
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @NotEmpty annotations.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenShortUsernameIsEmptyString() {
		try {
			user.setShortUsername("");			
		
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("shortUsername", violation.getPropertyPath().toString());
			assertEquals(NotEmpty.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for @Size(max = 40) annotations.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canNotPersistUserWhenShortUsernameExceedsFortyCharacters() {
		try {
			user.setShortUsername("aaaaabbbbbcccccdddddeeeeefffffgaaaaabbbbbcccccdddddeeeeefffffg");	// length = 62
			
			persist(user);
			
			fail("Expected ConstraintViolationException wasn't thrown");
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("shortUsername", violation.getPropertyPath().toString());
			assertEquals(Size.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
		}
	}
	
	/* -------------------------------------------------------------------------------------
	 * Test for when all annotations are valid.
	 * ------------------------------------------------------------------------------------ */
	@Test
	public void canPersistUserWhenShortUsernameIsNotEmptyAndIsLessThanFortyCharacters() {
		try {
			user.setShortUsername("Bob");			
		
			persist(user);
		} catch (ConstraintViolationException e) {
			assertEquals(1, e.getConstraintViolations().size());
			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
			
			assertEquals("shortUsername", violation.getPropertyPath().toString());
		}
		assertEquals(user.getShortUsername(), "Bob");
		assertTrue(contains(user));
	}

	/*
	 * Not sure how to test the role or enabled field as they do not have constraints on them.
	 */
}
