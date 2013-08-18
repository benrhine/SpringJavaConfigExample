package com.benrhine.spring.repository;

import java.io.Serializable;

import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseRepository<T> {
	private static final Logger logger = Logger.getLogger(BaseRepository.class);
	
	@Autowired private SessionFactory sessionFactory;
	
	private Session getCurrentSession() throws Exception {
		try {
			return sessionFactory.getCurrentSession();
		} catch (Exception e) {
			logger.error("Could not get current session ... ", e);
			throw new Exception("Could not get current session ... " + e);
		}
	}
	
	protected Object get(Class<T> cls, Serializable serial) throws HibernateException, Exception {
		try {
			return this.getCurrentSession().get(cls, serial);
		} catch (HibernateException e) {
			logger.error(e);
			throw new HibernateException("An error occured accessing the database ...", e);
		} catch (Exception e) {
			logger.error("Could not get current object ... ", e);
			throw new Exception("Could not get current object ... " + e.getMessage());
		}
	}
	
	protected void save(final Object obj) throws ConstraintViolationException, Exception {
		try {
			this.getCurrentSession().save(obj);
		} catch (ConstraintViolationException cve) {
			logger.error("Could not add new user due to constraint violation ... ", cve);
			throw new ConstraintViolationException("Constraint Violation: Could not add new user ... ", cve.getConstraintViolations());
		} catch (Exception e) {
			logger.error("Could not save current object ... ", e);
			throw new Exception("Could not save current object ... " + e.getMessage());
		}
	}
	
	protected void update(final Object obj) throws ConstraintViolationException, Exception {
		try {
			this.getCurrentSession().update(obj);
		} catch (ConstraintViolationException cve) {
			logger.error("Could not update user due to constraint violation ... ", cve);
			throw new ConstraintViolationException("Constraint Violation: Could not update user ... ", cve.getConstraintViolations());
		} catch (Exception e) {
			logger.error("Could not update current object ... ", e);
			throw new Exception("Could not update current object ... " + e.getMessage());
		}
	}
	
	protected void delete(final Object obj) throws Exception {
		try {
			this.getCurrentSession().delete(obj);
		} catch (Exception e) {
			logger.error("Could not delete current object ... ", e);
			throw new Exception("Could not delete current object ... " + e.getMessage());
		}
	}
	
	protected Query createQuery(final String query) throws HibernateException, Exception {
		try {
			return this.getCurrentSession().createQuery(query);
		} catch (HibernateException e) {
			logger.error(e);
			throw new HibernateException("An error occured accessing the database ...", e);
		} catch (Exception e) {
			logger.error("Could not return query ... ", e);
			throw new Exception("Could not return query ... " + e.getMessage());
		}
	}
}
