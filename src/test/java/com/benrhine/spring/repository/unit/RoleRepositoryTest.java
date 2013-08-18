package com.benrhine.spring.repository.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.benrhine.spring.domain.Role;
import com.benrhine.spring.domain.makers.RoleMaker;
import com.benrhine.spring.repository.RoleRepository;
import com.benrhine.spring.repository.RoleRepositoryImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class RoleRepositoryTest {
	/**
	 * @Mock annotation is used as opposed to importing configuration classes
	 * with the @ContextConfiguration(classes = { TestConfig.class }) as it is
	 * much cleaner and does the same thing.
	 */
	@Mock private Query query;
	@Mock private Session session;
	@Mock private SessionFactory sessionFactory;
	
	/**
	 * Use @InjectMocks to dynamically insert your mocked objects into the
	 * "LIVE" object you are testing.
	 */
	@InjectMocks private static RoleRepository roleRepository;
	
	/**
	 * Private domain object for testing.
	 */
	private Role role;
	
	/**
	 * Prior to the execution of tests instantiate the object that is to be
	 * tested.
	 * 
	 * Note: This executes ONLY ONCE.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void oneTimeSetup() throws Exception {
		roleRepository = new RoleRepositoryImpl();
	}
	
	/**
	 * Setup that occurs before every single test. Sets up mocks and
	 * creates a populated object for testing.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setupBeforeEveryMethod() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		role = RoleMaker.makeRole();
	}
	
	/**
	 * Tear down that occurs after every single test. Resets mocked
	 * objects to their original state.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDownAfterEveryMethod() throws Exception {
		Mockito.reset(session);
	}

	/**
	 * Add New Role Tests: Should cover throwing a new Exception.
	 * 
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void addNewRoleWhenRoleIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		roleRepository.addRole(null);
	}
	
	@Test
	public void addNewRoleWhenRoleIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new role ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(role);
		try {
			role.setRole(null);
			roleRepository.addRole(role);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new role ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void addNewRoleWhenUserIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not add new role ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).save(role);
		try {
			role.setUser(null);
			roleRepository.addRole(role);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new role ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	/**
	 * Add New Role Tests: Add New Role is successful.
	 * @throws Exception 
	 */
	
	@Test
	public void addNewRoleWhenRoleIsValid() {	
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.save(role)).thenReturn(true);
		try {
			roleRepository.addRole(role);
		} catch (Exception e) {
			fail("User should add successfully");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).save(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	/**
	 * Update Role Tests: Should cover throwing a new Exception.
	 * 
	 * @throws ConstraintViolationException
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void updateRoleWhenRoleIsNullShouldThrowException() throws ConstraintViolationException, Exception {
		roleRepository.updateRole(null);
	}
	
	@Test
	public void updateRoleWhenRoleIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update role ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(role);
		try {
			role.setRole(null);
			roleRepository.updateRole(role);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not add new role ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void updateRoleWhenUserIsNullConstraintShouldThrowConstraintViolation() {
		ConstraintViolationException cve = new ConstraintViolationException("Constraint Violation: Could not update role ... ", null);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(cve).when(session).update(role);
		try {
			role.setUser(null);
			roleRepository.updateRole(role);
		} catch (ConstraintViolationException e) {
			assertEquals("Constraint Violation: Could not update role ... ", e.getMessage());
		} catch (Exception e) {
			fail("We should only have a constraint violation.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	/**
	 * Update Role Tests: Update Role is successful.
	 * @throws Exception 
	 */
	
	@Test
	public void updateRoleWhenRoleIsValid() {	
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doNothing().when(session).update(role);
		try {
			roleRepository.updateRole(role);
		} catch (Exception e) {
			fail("User should update successfully");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).update(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	/**
	 * Delete Role Tests: Should cover throwing a new Exception.
	 * 
	 * @throws Exception
	 */
	@Test(expected = Exception.class)
	public void deleteUserWhenUserIsNullShouldThrowException() throws Exception {
		roleRepository.deleteRole(null);
	}
	
	@Test
	public void deleteUserWhenUserIsValid() {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doNothing().when(session).delete(role);
		try {
			roleRepository.deleteRole(role);
		} catch (Exception e) {
			fail("Role should add successfully");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).delete(Mockito.any(Role.class));
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = RuntimeException.class)
	public void findRoleByIdThrowsRuntimeException() throws RuntimeException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new RuntimeException()).when(session).get((Class<Role>) Mockito.anyObject(), Mockito.anyLong());
		
		roleRepository.findById(1L);
		
		Mockito.verify(session, VerificationModeFactory.times(1)).get((Class<Role>) Mockito.anyObject(), Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = HibernateException.class)
	public void findRoleByIdThrowsHibernateException() throws HibernateException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(session).get((Class<Role>) Mockito.anyObject(), Mockito.anyLong());
		
		roleRepository.findById(1L);
		
		Mockito.verify(session, VerificationModeFactory.times(1)).get((Class<Role>) Mockito.anyObject(), Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findRoleByIdIsSuccessful() {
		role.setId(1L);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doReturn(role).when(session).get((Class<Role>) Mockito.anyObject(), Mockito.anyLong());
		
		try {
			Role r = roleRepository.findById(1L);
			
			assertNotNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).get((Class<Role>) Mockito.anyObject(), Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void findRoleByIdFails(){
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doReturn(null).when(session).get((Class<Role>) Mockito.anyObject(), Mockito.anyLong());
		
		try {
			Role r = roleRepository.findById(1L);
			
			assertNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).get((Class<Role>) Mockito.anyObject(), Mockito.anyLong());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test(expected = RuntimeException.class)
	public void findByRoleThrowsRuntimeException() throws RuntimeException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new RuntimeException()).when(session).createQuery(Mockito.anyString());
		
		roleRepository.findByRole(1L);
		
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test(expected = HibernateException.class)
	public void findByRoleThrowsHibernateException() throws HibernateException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(session).createQuery(Mockito.anyString());
		
		roleRepository.findByRole(1L);
		
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findByRoleIsSuccessful() {
		List<Role> lst = new ArrayList<Role>();
		lst.add(role);
		
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(lst);
		
		try {
			List<Role> r = roleRepository.findByRole(1L);
			
			assertNotNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findByRoleFails(){
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(null);
		
		try {
			List<Role> r = roleRepository.findByRole(1L);
			
			assertNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test(expected = RuntimeException.class)
	public void findAllRolesThrowsRuntimeException() throws RuntimeException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new RuntimeException()).when(session).createQuery(Mockito.anyString());
		
		roleRepository.findAllRoles();
		
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test(expected = HibernateException.class)
	public void findAllRolesThrowsHibernateException() throws HibernateException {
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.doThrow(new HibernateException("An error occured accessing the database ...")).when(session).createQuery(Mockito.anyString());
		
		roleRepository.findAllRoles();
		
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findAllRolesIsSuccessful() {
		List<Role> lst = new ArrayList<Role>();
		lst.add(role);
		
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(lst);
		
		try {
			List<Role> r = roleRepository.findAllRoles();
			
			assertNotNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should not be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
	
	@Test
	public void findAllRolesFails(){
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		Mockito.when(session.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(null);
		
		try {
			List<Role> r = roleRepository.findAllRoles();
			
			assertNull(r);
		} catch (Exception e) {
			fail("No Exceptions should be thrown: user should be null.");
		}
		Mockito.verify(session, VerificationModeFactory.times(1)).createQuery(Mockito.anyString());
		Mockito.verifyNoMoreInteractions(session);
	}
}
