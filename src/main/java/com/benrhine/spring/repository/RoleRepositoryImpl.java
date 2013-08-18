package com.benrhine.spring.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.benrhine.spring.domain.Role;

@Repository
@Transactional
public class RoleRepositoryImpl extends BaseRepository<Role> implements RoleRepository {
	private static final Logger logger = Logger.getLogger(RoleRepositoryImpl.class);
	
	public void addRole(final Role role) throws ConstraintViolationException, Exception {
		try {
			save(role);
//		} catch (ConstraintViolationException cve) {
//			logger.error("Could not add new role due to constraint violation ... ", cve);
//			throw new ConstraintViolationException("Constraint Violation: Could not add new role ... ", cve.getSQLException(), null);
		} catch (Exception e) {
			logger.error("Could not add new role ...", e);
			throw new Exception("Could not add new role ...", e);
		}
	}
	
	public void updateRole(final Role role) throws ConstraintViolationException, Exception {
		try {
			update(role);
//		} catch (ConstraintViolationException cve) {
//			logger.error("Could not update role due to constraint violation ... ", cve);
//			throw new ConstraintViolationException("Constraint Violation: Could not update role ... ", cve.getSQLException(), null);
		} catch (Exception e) {
			logger.error("Could not update role ...", e);
			throw new Exception("Could not update role ...", e);
		}
	}
	
	public void deleteRole(final Role role) throws Exception {
		try {
			delete(role);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception("Could not delete role ...", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> findByRole(final Long roletype) throws HibernateException, RuntimeException {
		try {
			Query query = createQuery("from Role as r where r.role = :roletype");
			query.setLong("roletype", roletype);
			
			return query.list();
		} catch (HibernateException e) {
			logger.error(e);
			throw new HibernateException("An error occured accessing the database ...", e);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException("No Role with username = " + roletype + " found.", e);
		}
	}
	
	public Role findById(final Long id) throws HibernateException, RuntimeException {
		try {
			return (Role) get(Role.class, id);
		} catch (HibernateException e) {
			logger.error(e);
			throw new HibernateException("An error occured accessing the database ...", e);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException("No Role with Id = " + id + " found.", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> findAllRoles() throws HibernateException, RuntimeException {
		try {
			return createQuery("from Role").list();
		} catch (HibernateException e) {
			logger.error(e);
			throw new HibernateException("An error occured accessing the database ...", e);
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException("Could not return roles list ...", e);
		}
	}
}
