package com.benrhine.spring.util.unit;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.benrhine.spring.util.RoleUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class RoleUtilTest {
	
	@Test
	public void roleUtilAdminRoleIsOne() {
		assert(RoleUtil.ROLE_ADMIN == 1L);
	}
	
	@Test
	public void roleUtilUserRoleIsTwo() {
		assert(RoleUtil.ROLE_USER== 2L);
	}
	
	@Test
	public void roleUtilMethodRolesReturnsAllRoles() {
		List<Long> lst = RoleUtil.roles();
		assertEquals(lst.size(), 2);
	}

}
