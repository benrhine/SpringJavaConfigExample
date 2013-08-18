package com.benrhine.spring.service;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.benrhine.spring.domain.User;
import com.benrhine.spring.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired private UserRepository userRepository;
	
	public Boolean addUser(@Valid final User user) throws ConstraintViolationException, Exception {
		try {
			User existingUser = this.findByUsername(user.getUsername()); //maybe a testing issue
			
			if (existingUser != null) {
				return false;
			}
			user.getRole().setUser(user);
			userRepository.addUser(user);
			
			return true;	
		} catch (ConstraintViolationException cve) {
			logger.error("Could not add new user due to constraint violation ... ", cve);
			throw new ConstraintViolationException("Constraint Violation: Could not add new user ... ", cve.getConstraintViolations());
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}
	
	/*
	 * Limits what fields on a user object can be updated
	 */
	public Boolean updateUser(@Valid final User user) throws ConstraintViolationException, Exception {
		try {
			User existingUser = this.findByUsername(user.getUsername()); //maybe a testing issue
			
			if (existingUser == null) {
				return false;
			}
			// Only firstName, lastName, and role fields are updatable... should this be true?
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.getRole().setRole(user.getRole().getRole());;
			userRepository.updateUser(existingUser);
			
			return true;
		} catch (ConstraintViolationException cve) {
			logger.error("Could not update user due to constraint violation ... ", cve);
			throw new ConstraintViolationException("Constraint Violation: Could not update user ... ", cve.getConstraintViolations());
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}
	
	public Boolean deleteUser(@Valid final User user) throws Exception {
		try {
			User existingUser = this.findByUsername(user.getUsername()); //maybe a testing issue
			
			if (existingUser == null) {
				return false;
			}
			userRepository.deleteUser(existingUser);
			
			User deletedUser = userRepository.findById(existingUser.getId());
			
			if (deletedUser != null) {
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}
	
	public User findByUsername(final String username) throws HibernateException, RuntimeException {
		try {
			return this.userRepository.findByUsername(username);
		} catch (HibernateException he) {
			logger.error(he);
			throw new HibernateException(he);
		} catch (RuntimeException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public User findById(final Long id) throws HibernateException, RuntimeException {
		try {
			return this.userRepository.findById(id);
		} catch (HibernateException he) {
			logger.error(he);
			throw new HibernateException(he);
		} catch (RuntimeException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public List<User> findUsersByRole(final Long role) throws HibernateException, RuntimeException { // needs integration testing to ensure it works properly
		try {
			return this.userRepository.findUsersByRole(role);
		} catch (HibernateException he) {
			logger.error(he);
			throw new HibernateException(he);
		} catch (RuntimeException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public List<User> findAllUsers() throws HibernateException, RuntimeException {
		try {
			return this.userRepository.findAllUsers();
		} catch (HibernateException he) {
			logger.error(he);
			throw new HibernateException(he);
		} catch (RuntimeException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

}
