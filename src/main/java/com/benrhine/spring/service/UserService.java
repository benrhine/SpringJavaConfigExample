package com.benrhine.spring.service;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.hibernate.HibernateException;

import com.benrhine.spring.domain.User;

public interface UserService {

	public Boolean addUser(@Valid final User user) 		throws ConstraintViolationException, Exception;
	public Boolean updateUser(@Valid final User user) 	throws ConstraintViolationException, Exception;
	public Boolean deleteUser(@Valid final User user) 	throws Exception;
	
	public User findByUsername(final String username) 	throws HibernateException, RuntimeException;
	public User findById(final Long id) 				throws HibernateException, RuntimeException;
	public List<User> findUsersByRole(final Long role) 	throws HibernateException, RuntimeException;
	public List<User> findAllUsers() 					throws HibernateException, RuntimeException;
}
