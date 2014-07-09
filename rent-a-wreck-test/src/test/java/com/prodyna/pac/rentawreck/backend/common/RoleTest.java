/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common;

import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.model.Role;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.common.service.RoleService;

/**
 * RoleTest
 * 
 * @author Oliver Teichmann
 * 
 */
@RunWith(Arquillian.class)
//@Transactional
public class RoleTest extends AbstractEntityCRUDTest<Role> {
	
	@Inject
	private RoleService service;
	
	@Deployment
	public static WebArchive createDeployment() {
		return TestDeploymentFactory.getInstance().getBackendCommonDeployment();
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<Role> getService() {
		return service;
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
		return role;
	}

}
