package com.benrhine.spring.util;

import java.util.ArrayList;
import java.util.List;

public class RoleUtil {
	public static final Long ROLE_ADMIN = 1L;
	public static final Long ROLE_USER = 2L;
	
	public static final List<Long> roles() {
		List<Long> roles = new ArrayList<Long>();
		roles.add(ROLE_USER);
		roles.add(ROLE_ADMIN);
		return roles;
	}
}
