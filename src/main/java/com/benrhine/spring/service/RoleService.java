package com.benrhine.spring.service;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.HibernateException;

import com.benrhine.spring.domain.Role;

public interface RoleService {

	public Boolean addRole(@Valid final Role role) 		throws Exception;
	public Boolean updateRole(@Valid final Role role) 	throws Exception;
	public Boolean deleteRole(@Valid final Role role) 	throws Exception;
	
	public Role findById(final Long id) 				throws HibernateException, RuntimeException;
	public List<Role> findByRole(final Long role) 		throws HibernateException, RuntimeException;
	public List<Role> findAllRoles() 					throws HibernateException, RuntimeException;
}
