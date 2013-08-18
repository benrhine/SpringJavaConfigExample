package com.benrhine.spring.repository;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import com.benrhine.spring.domain.User;

public interface UserRepository {
	/**
	 * Basic CRUD functions:
	 * 
	 * @param user
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	public void addUser(@Valid final User user) throws ConstraintViolationException, Exception;
	public void updateUser(@Valid final User user) throws ConstraintViolationException, Exception;
	public void deleteUser(@Valid final User user) throws Exception;
	
	/**
	 * Finder Methods:
	 */
	public User findByUsername(final String username) throws HibernateException, RuntimeException ;
	public User findById(final Long id) throws HibernateException, RuntimeException ;
	public List<User> findUsersByRole(final Long role) throws HibernateException, RuntimeException ;
	public List<User> findAllUsers() throws HibernateException, RuntimeException ;
}
