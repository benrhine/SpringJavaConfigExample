package com.benrhine.spring.repository;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.benrhine.spring.domain.Role;

public interface RoleRepository {
	/**
	 * Basic CRUD functions:
	 * 
	 * @param role
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	public void addRole(final Role role) throws ConstraintViolationException, Exception;
	public void updateRole(final Role role) throws ConstraintViolationException, Exception;
	public void deleteRole(final Role role) throws Exception;
	
	/**
	 * Finder Methods:
	 */
	public List<Role> findByRole(final Long roletype) throws HibernateException, RuntimeException;
	public Role findById(final Long id) throws HibernateException, RuntimeException;
	public List<Role> findAllRoles() throws HibernateException, RuntimeException;
}
