/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common;

import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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
public class RoleTest extends AbstractBackendCommonTest<Role> {
	
	@Inject
	private RoleService service;
	
	@Deployment
	public static WebArchive createDeployment() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.common");
		wa.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		wa.addAsResource("META-INF/beans.xml");
		wa.addAsWebInfResource("test-ds.xml", "test-ds.xml");
		System.out.println(wa.toString(true));

		return wa;
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
	public Role getCRUDEntity() {
		Role role = new Role();
		role.setUuid(UUID.randomUUID().toString());
		role.setUsername("user");
		role.setRoleGroup("Roles");
		role.getRoles().add("role1");
		role.getRoles().add("role2");
		
		return role;
	}

}
