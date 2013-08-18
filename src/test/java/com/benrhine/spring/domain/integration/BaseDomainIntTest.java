package com.benrhine.spring.domain.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InMemoryDBConfig.class }, loader = AnnotationConfigContextLoader.class)
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class BaseDomainIntTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired private SessionFactory sessionFactory;
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	protected void flush() {
		this.getCurrentSession().flush();
	}
	
	protected void clear() {
		this.getCurrentSession().clear();
	}
	
	protected void persist(final Object obj) {
		this.getCurrentSession().persist(obj);
	}
	
	protected Boolean contains(final Object obj) {
		return this.getCurrentSession().contains(obj);
	}
}
