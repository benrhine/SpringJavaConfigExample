package com.benrhine.spring.domain.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.benrhine.spring.domain.Role;
import com.benrhine.spring.domain.makers.RoleMaker;
import com.benrhine.spring.domain.makers.UserMaker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class RoleTest {
	private Role role;
	
	/*
	 * Test Constructors
	 */
	@Test
	public void createNewRoleWhenEmptyConstructor() {
		try {
			role = new Role();
		} catch (Exception e) {
			fail("A new empty role object should have been created.");
		}
	}
	
	@Test
	public void createNewUserWhenObjectConstructor() {
		Role test = RoleMaker.makeRole();
		try {
			role = new Role(test);
			
			assertEquals(test.getRole(), role.getRole());
			assertEquals(test.getUser(), role.getUser());
		} catch (Exception e) {
			fail("A new copy of a role object should have been created.");
		}
	}
	
	@Test
	public void createNewUserWhenFullConstructor() {
		Role test = RoleMaker.makeRole();
		try {
			role = new Role(2L, UserMaker.makeUser("test5@fake.com"));
			
			assertEquals(test.getRole(), role.getRole());
			assertEquals(test.getUser().getClass(), role.getUser().getClass());
		} catch (Exception e) {
			fail("A new role object should have been created.");
		}
	}
	
	/*
	 * Test Getter's
	 */
	@Test
	public void getId() {
		Role test = RoleMaker.makeRole();
		
		assertEquals(test.getId(), null);
	}
	
	@Test
	public void getRole() {
		Role test = RoleMaker.makeRole();
		
		assert(test.getRole() == 2L);
	}
	
	@Test
	public void getUser() {
		Role test = RoleMaker.makeRole();
		
		assertEquals(test.getUser().getUsername(), "test@fake.com");
	}
	
	/*
	 * Test Setter's
	 */
	@Test
	public void setId() {
		Role test = RoleMaker.makeRole();
		test.setId(4L);
		
		assert(test.getId() == 4L);
	}
	@Test
	public void setRole() {
		Role test = new Role();
		
		test.setRole(3L);
		
		assert(test.getRole() == 3L);
	}
	
	@Test
	public void setUser() {
		Role test = new Role();
		
		test.setUser(UserMaker.makeUser("testOne@fake.com"));
		
		assertEquals(test.getUser().getUsername(), "testOne@fake.com");
	}

}
