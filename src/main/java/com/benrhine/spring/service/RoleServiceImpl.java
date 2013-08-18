package com.benrhine.spring.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.benrhine.spring.domain.Role;
import com.benrhine.spring.repository.RoleRepository;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	private static final Logger logger = Logger.getLogger(RoleServiceImpl.class);

	@Autowired private RoleRepository roleRepository;
	
	public Boolean addRole(final Role role) throws Exception {
		try {
			Role existingRole = this.findById(role.getId());
			
			if (existingRole != null) {
				return false;
			}
			roleRepository.addRole(role);
			
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}
	
	public Boolean updateRole(final Role role) throws Exception {
		try {
			Role existingRole = this.findById(role.getId());
			
			if (existingRole == null) {
				return false;
			}
			existingRole.setRole(role.getRole());
			existingRole.setUser(role.getUser());
			roleRepository.updateRole(existingRole);
			
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}
	
	public Boolean deleteRole(final Role role) throws Exception {
		try {
			Role existingRole = this.findById(role.getId());
			
			if (existingRole == null) {
				return false;
			}
			roleRepository.deleteRole(existingRole);
			
			Role deletedRole = this.findById(role.getId());
			
			if (deletedRole != null) {
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}
	
	public List<Role> findByRole(final Long roletype) throws HibernateException, RuntimeException { //needs some work should be some kind of a list
		try {
			return roleRepository.findByRole(roletype);
		} catch (HibernateException he) {
			logger.error(he);
			throw new HibernateException(he);
		} catch (RuntimeException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public Role findById(final Long id) throws HibernateException, RuntimeException {
		try {
			return roleRepository.findById(id);
		} catch (HibernateException he) {
			logger.error(he);
			throw new HibernateException(he);
		} catch (RuntimeException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
	
	public List<Role> findAllRoles() throws HibernateException, RuntimeException {
		try {
			return roleRepository.findAllRoles();
		} catch (HibernateException he) {
			logger.error(he);
			throw new HibernateException(he);
		} catch (RuntimeException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}
}
