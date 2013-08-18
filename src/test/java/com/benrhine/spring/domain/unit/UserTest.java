package com.benrhine.spring.domain.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.benrhine.spring.domain.Role;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.domain.makers.RoleMaker;
import com.benrhine.spring.domain.makers.UserMaker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class UserTest {
	private User user;
	
	/*
	 * Test Constructors
	 */
	@Test
	public void createNewUserWhenEmptyConstructor() {
		try {
			user = new User();
		} catch (Exception e) {
			fail("A new empty user object should have been created.");
		}
	}
	
	@Test
	public void createNewUserWhenObjectConstructor() {
		User test = UserMaker.makeUser("test@fake.com");
		try {
			user = new User(test);
			
			assertEquals(test.getFirstName(), 		user.getFirstName());
			assertEquals(test.getLastName(), 		user.getLastName());
			assertEquals(test.getUsername(), 		user.getUsername());
			assertEquals(test.getPassword(), 		user.getPassword());
			assertEquals(test.getEnabled(), 		user.getEnabled());
			//assertEquals(test.getRole(), 			user.getRole());
			assertEquals(test.getShortUsername(), 	user.getShortUsername());
		} catch (Exception e) {
			fail("A new copy of a user object should have been created.");
		}
	}
	
	@Test
	public void createNewUserWhenFullConstructor() {
		User test = UserMaker.makeUser("test@fake.com");
		try {
			user = new User("Test", "User", "test@fake.com", "password", 1, "tUser", RoleMaker.makeRole());
			
			assertEquals(test.getFirstName(), 		user.getFirstName());
			assertEquals(test.getLastName(), 		user.getLastName());
			assertEquals(test.getUsername(), 		user.getUsername());
			assertEquals(test.getPassword(), 		user.getPassword());
			assertEquals(test.getEnabled(), 		user.getEnabled());
			assertEquals(test.getShortUsername(), 	user.getShortUsername());
		} catch (Exception e) {
			fail("A new user object should have been created.");
		}
	}
	
	/*
	 * Test Getter's
	 */
	@Test
	public void getUserId() {
		User test = UserMaker.makeUser("test@fake.com");
		
		assertEquals(test.getId(), null);
	}
	
	@Test
	public void getUserFirstName() {
		User test = UserMaker.makeUser("test@fake.com");
		
		assertEquals(test.getFirstName(), "Test");
	}
	
	@Test
	public void getUserLastName() {
		User test = UserMaker.makeUser("test@fake.com");
		
		assertEquals(test.getLastName(), "User");
	}
	
	@Test
	public void getUserUsername() {
		User test = UserMaker.makeUser("test@fake.com");
		
		assertEquals(test.getUsername(), "test@fake.com");
	}
	
	@Test
	public void getUserPassword() {
		User test = UserMaker.makeUser("test@fake.com");
		
		assertEquals(test.getPassword(), "password");
	}
	
	@Test
	public void getUserShortUsername() {
		User test = UserMaker.makeUser("test@fake.com");
		
		assertEquals(test.getShortUsername(), "tUser");
	}
	
	@Test
	public void getUserEnabled() {
		User test = UserMaker.makeUser("test@fake.com");
		
		assert(test.getEnabled() == 1);
	}
	
	@Test
	public void getUserRole() {
		User test = UserMaker.makeUser("test@fake.com");
		
		assertEquals(test.getRole().getClass(), Role.class);
	}
	
	/*
	 * Test Setter's
	 */
	@Test
	public void setUserId() {
		User test = new User();
		test.setId(4L);
		
		assert(test.getId() == 4L);
	}
	@Test
	public void setUserFirstName() {
		User test = new User();
		
		test.setFirstName("TestOne");
		
		assertEquals(test.getFirstName(), "TestOne");
	}
	
	@Test
	public void setUserLastName() {
		User test = new User();
		
		test.setLastName("UserOne");
		
		assertEquals(test.getLastName(), "UserOne");
	}
	
	@Test
	public void setUserUsername() {
		User test = new User();
		
		test.setUsername("test1@fake.com");
		
		assertEquals(test.getUsername(), "test1@fake.com");
	}
	
	@Test
	public void setUserPassword() {
		User test = new User();
		
		test.setPassword("passwordOne");
		
		assertEquals(test.getPassword(), "passwordOne");
	}
	
	@Test
	public void setUserShortUsername() {
		User test = new User();
		
		test.setShortUsername("tUserOne");
		
		assertEquals(test.getShortUsername(), "tUserOne");
	}
	
	@Test
	public void setUserEnabled() {
		User test = new User();
		
		test.setEnabled(2);
		
		assert(test.getEnabled() == 2);
	}
	
//	@Test
//	public void setUserRole() {
//		User test = new User();
//		Role role = RoleMaker.makeRole();
//		
//		test.setRole(role);
//		
//		assertEquals(test.getRole(), role);
//	}
}
