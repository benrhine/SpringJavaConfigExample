//package com.benrhine.spring.domain.integration;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.validator.constraints.NotEmpty;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.benrhine.spring.config.integration.InMemoryDBConfig;
//import com.benrhine.spring.domain.RegisteredUser;
//import com.benrhine.spring.domain.Role;
//import com.benrhine.spring.domain.makers.RegisteredUserMaker;
//import com.benrhine.spring.domain.makers.RoleMaker;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { InMemoryDBConfig.class }, loader  = AnnotationConfigContextLoader.class)
//@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
//@Transactional
//public class RegisteredUserIntTest {
//	@Autowired private SessionFactory sessionFactory;
//	
//	private RegisteredUser registeredUser;
//	private Role role;
//	
//	@Before
//	public void initData() {
//		registeredUser = RegisteredUserMaker.makeRegisteredUser("test@fake.com");
//		role = RoleMaker.makeRole();
//		
//		sessionFactory.getCurrentSession().persist(registeredUser);
//	}
//	
////	@After
////	public void cleanUpData() {
////		sessionFactory.getCurrentSession().flush();
////		sessionFactory.getCurrentSession().clear();
////	}
//	
//	/*
//	 * Simple test to ensure database is up and running and that we have actually
//	 * persisted a object.
//	 */
//	@Test
//	public void showThatRegisteredUserHasBeenPersisted() {
//		assertTrue(sessionFactory.getCurrentSession().contains(registeredUser));
//	}
//	
//	/*
//	 * Constructor Tests:
//	 * 
//	 * Validate that our constructors are properly creating our objects.
//	 */
//	@Test
//	public void basicConstructor() {
//		RegisteredUser registeredUser = new RegisteredUser();
//		
//		assertEquals(registeredUser.getFirstName(), 	null);
//		assertEquals(registeredUser.getLastName(),  	null);
//		assertEquals(registeredUser.getUsername(),  	null);
//		assertEquals(registeredUser.getPassword(),  	null);
//		assertEquals(registeredUser.getEnabled(),   	null);
//		assertEquals(registeredUser.getShortUsername(), null);
//		assertEquals(registeredUser.getRole(), 			null);
//	}
//	
//	@Test
//	public void objectConstructor() {
//		RegisteredUser registeredUser = new RegisteredUser(this.registeredUser);
//		
//		assert(registeredUser.getEnabled() == 1);
//		assertEquals(registeredUser.getFirstName(), 	"Test");
//		assertEquals(registeredUser.getLastName(),  	"User");
//		assertEquals(registeredUser.getUsername(),  	"test@fake.com");
//		assertEquals(registeredUser.getPassword(),  	"password");
//		assertEquals(registeredUser.getShortUsername(), "tUser");
//		assertEquals(registeredUser.getRole().getClass(), Role.class);
//	}
//	
//	@Test
//	public void twoArgConstructor() {
//		RegisteredUser registeredUser = new RegisteredUser("bob", role);
//		
//		assertEquals(registeredUser.getFirstName(), 	null);
//		assertEquals(registeredUser.getLastName(),  	null);
//		assertEquals(registeredUser.getUsername(),  	null);
//		assertEquals(registeredUser.getPassword(),  	null);
//		assertEquals(registeredUser.getEnabled(),   	null);
//		assertEquals(registeredUser.getShortUsername(), "bob");
//		assertEquals(registeredUser.getRole(), 			role);
//	}
//	
//	@Test
//	public void fullConstructor() {
//		RegisteredUser registeredUser = new RegisteredUser("Fake", "Test", "ft@fake.com", "password", 1, "ft", role);
//		
//		assert(registeredUser.getEnabled() == 1);
//		assertEquals(registeredUser.getFirstName(), 	"Fake");
//		assertEquals(registeredUser.getLastName(),  	"Test");
//		assertEquals(registeredUser.getUsername(),  	"ft@fake.com");
//		assertEquals(registeredUser.getPassword(),  	"password");
//		assertEquals(registeredUser.getShortUsername(), "ft");
//		assertEquals(registeredUser.getRole(), 			role);
//	}
//
//	/*
//	 * Constraint Tests:
//	 */
//	@Test
//	public void firstNameCanNotBeNull() {
//		try {
//			registeredUser.setShortUsername(null);			
//		
//			sessionFactory.getCurrentSession().update(registeredUser);
//			
//			fail("Expected ConstraintViolationException wasn't thrown");
//		} catch (ConstraintViolationException e) {
//			assertEquals(2, e.getConstraintViolations().size());
//			Object[] violations = e.getConstraintViolations().toArray();
//			
//			for (Object violation:violations) {
//				assertEquals("shortUsername", ((ConstraintViolation<?>) violation).getPropertyPath().toString());
//				
//				if (((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType().equals(NotEmpty.class)) {
//					assertEquals(NotEmpty.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
//				} else {
//					assertEquals(NotNull.class, ((ConstraintViolation<?>) violation).getConstraintDescriptor().getAnnotation().annotationType());
//				}
//			}
//		}
//	}
//	
//	@Test
//	public void firstNameCanNotBeEmptyString() {
//		try {
//			registeredUser.setFirstName("");			
//		
//			sessionFactory.getCurrentSession().persist(registeredUser);
//			
//			fail("Expected ConstraintViolationException wasn't thrown");
//		} catch (ConstraintViolationException e) {
//			assertEquals(1, e.getConstraintViolations().size());
//			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
//			
//			assertEquals("firstName", violation.getPropertyPath().toString());
//			assertEquals(NotEmpty.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
//		}
//	}
//	
//	@Test
//	public void firstNameCanNotExceedThirtyCharacters() {
//		try {
//			registeredUser.setFirstName("aaaaabbbbbcccccdddddeeeeefffffg");	// length = 31	
//		
//			sessionFactory.getCurrentSession().persist(registeredUser);
//			
//			fail("Expected ConstraintViolationException wasn't thrown");
//		} catch (ConstraintViolationException e) {
//			assertEquals(1, e.getConstraintViolations().size());
//			ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
//			
//			assertEquals("firstName", violation.getPropertyPath().toString());
//			assertEquals(Size.class, violation.getConstraintDescriptor().getAnnotation().annotationType());
//		}
//	}
//}
