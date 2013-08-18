package com.benrhine.spring.repository.integration;

import java.io.Serializable;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.benrhine.spring.config.integration.InMemoryDBConfig;
import com.benrhine.spring.domain.User;
import com.benrhine.spring.repository.UserRepository;
import com.benrhine.spring.repository.UserRepositoryImpl;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InMemoryDBConfig.class }, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class BaseRepositoryIntTest<T> extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired private SessionFactory sessionFactory;
	private UserRepository userRepository = new UserRepositoryImpl();
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected Object get(Class<T> cls, Serializable serial) {
		return this.getCurrentSession().get(cls, serial);
	}
	
	protected void save(final Object obj) {
		this.getCurrentSession().save(obj);
	}
	
	protected void update(final Object obj) {
		this.getCurrentSession().update(obj);
	}
	
	protected void delete(final Object obj) {
		this.getCurrentSession().delete(obj);
	}
	
	protected Query createQuery(final String query) {
		return this.getCurrentSession().createQuery(query);
	}
	
	protected void addUser(final User user) throws ConstraintViolationException, Exception {
		userRepository.addUser(user);
	}
}
