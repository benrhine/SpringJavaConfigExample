package com.benrhine.spring.repository;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.benrhine.spring.domain.User;

@Repository
@Transactional
public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {
	private static final Logger logger = Logger.getLogger(UserRepositoryImpl.class);
	
	public void addUser(@Valid final User user) throws ConstraintViolationException, Exception {
		try {
			save(user);
		} catch (ConstraintViolationException cve) {
			logger.error("Could not add new user due to constraint violation ... ", cve);
			throw new ConstraintViolationException("Constraint Violation: Could not add new user ... ", cve.getConstraintViolations());
		} catch (Exception e) {
			logger.error("Could not add new user ...", e);
			throw new Exception("Could not add new user ...", e);
		}
	}
	
	public void updateUser(@Valid final User user) throws ConstraintViolationException, Exception {
		try {
			update(user);
		} catch (ConstraintViolationException cve) {
			logger.error("Could not update user due to constraint violation ... ", cve);
			throw new ConstraintViolationException("Constraint Violation: Could not update user ... ", cve.getConstraintViolations());
		} catch (Exception e) {
			logger.error("Could not update user ...", e);
			throw new Exception("Could not update user ...", e);
		}
	}
	
	public void deleteUser(@Valid final User user) throws Exception {
		try {
			delete(user);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("Could not delete user ...", e);
		}
	}
	
	public User findByUsername(final String username) throws HibernateException, RuntimeException {
		try {
			Query query = createQuery("from User as u where u.userName = :username");
			query.setString("username", username);
			List<?> tmpLst = query.list();
			if ((tmpLst != null) && (tmpLst.size() == 1)) {
				return (User) tmpLst.get(0);
			}
			return null;
		} catch (HibernateException e) {
			logger.error(e);
			throw new HibernateException("An error occured accessing the database ...", e);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException("No User with username = " + username + " found.", e);
		}
	}
	
	public User findById(final Long id) throws HibernateException, RuntimeException {
		try {
			return (User) get(User.class, id);
		} catch (HibernateException e) {
			logger.error(e);
			throw new HibernateException("An error occured accessing the database ...", e);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException("No User with Id = " + id + " found.", e);
		}
	}
	/**
	 * These needs to be tested with integration test to be sure.
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUsersByRole(final Long role) throws HibernateException, RuntimeException {
		try {
			Query query = createQuery("from User as u where u.role.id = :role");
			query.setLong("role", role);
			
			return query.list();
		} catch (HibernateException e) {
			logger.error(e);
			throw new HibernateException("An error occured accessing the database ...", e);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException("No Users with role = " + role + " found.", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() throws HibernateException, RuntimeException {
		try {
			return createQuery("from User").list();
		} catch (HibernateException e) {
			logger.error(e);
			throw new HibernateException("An error occured accessing the database ...", e);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException("Could not return users list ...", e);
		}
	}
}
