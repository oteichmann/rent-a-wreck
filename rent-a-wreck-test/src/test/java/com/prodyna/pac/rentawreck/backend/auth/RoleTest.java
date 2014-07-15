/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.auth;

import static org.junit.Assert.*;

import java.util.UUID;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.picketbox.util.StringUtil;

import com.prodyna.pac.rentawreck.backend.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.model.Role;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityCRUDTest;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.common.service.RoleService;
import com.prodyna.pac.rentawreck.backend.common.service.exception.ValidationException;

/**
 * RoleTest
 * 
 * @author Oliver Teichmann
 * 
 */
@RunWith(Arquillian.class)
@Transactional
public class RoleTest extends AbstractEntityCRUDTest<Role> {
	
	@Inject
	private RoleService roleService;
	
	@Deployment
	public static WebArchive createDeployment() {
		return TestDeploymentFactory.getInstance().getBackendAuthDeployment();
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<Role> getService() {
		return roleService;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getCRUDEntity()
	 */
	@Override
	public Role createCRUDEntity() {
		Role role = new Role();
		role.setUuid(UUID.randomUUID().toString());
		role.setName("user");
		return role;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#updateCRUDEntity(com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	protected Role updateCRUDEntity(Role role) {
		role.setName("Test");
		return role;
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#assertsAfterUpdate(com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	protected void assertsAfterUpdate(Role role) {
		assertEquals("Test", role.getName());
	}
	
	@Test
	@InSequence(1)
	@Transactional(TransactionMode.ROLLBACK)
	public void testNullConstraints(){
		Role role = new Role();
		
		try {
			roleService.create(role);
			fail("Should fail because of not null constraints.");
		} catch (ValidationException e) {
			assertFalse(StringUtil.isNullOrEmpty(e.getMessage()));
		}
	}
	
	@Test
	@InSequence(2)
	@Transactional(TransactionMode.ROLLBACK)
	public void testUniqueConstraints(){
		Role roleA = new Role();
		roleA.setUuid(UUID.randomUUID().toString());
		roleA.setName("test");
		roleService.create(roleA);
		
		Role roleB = new Role();
		roleB.setUuid(UUID.randomUUID().toString());
		roleB.setName("test");
		try {
			roleService.create(roleB);
			fail("Should fail because of unique constraint violation.");
		} catch (EJBTransactionRolledbackException e) {
			assertTrue(e.getCause() instanceof PersistenceException);
		}
	}
	
	@Test
	@InSequence(3)
	@Transactional(TransactionMode.ROLLBACK)
	public void testFindByName(){
		Role role = new Role();
		role.setUuid(UUID.randomUUID().toString());
		role.setName("test");
		roleService.create(role);
		
		Role findByName = roleService.findByName("does-not-exist");
		assertNull(findByName);
		findByName = roleService.findByName("test");
		assertNotNull(findByName);
	}

}
