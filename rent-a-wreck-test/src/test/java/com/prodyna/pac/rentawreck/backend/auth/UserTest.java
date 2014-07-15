package com.prodyna.pac.rentawreck.backend.auth;

import static org.junit.Assert.*;

import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityCRUDTest;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;

@RunWith(Arquillian.class)
@Transactional
public class UserTest extends AbstractEntityCRUDTest<User> {

	@Inject
	private UserService service;
	
	@Deployment
	public static WebArchive createDeployment() {
		return TestDeploymentFactory.getInstance().getBackendAuthDeployment();
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<User> getService() {
		return service;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getCRUDEntity()
	 */
	@Override
	public User createCRUDEntity() {
		User user = new User();
		user.setUuid(UUID.randomUUID().toString());
		user.setUsername("admin");
		user.setPassword("admin");
		user.setFirstName("Rent-A-Wreck");
		user.setLastName("Administrator");
		user.setEmail("admin@rent-a-wreck.com");
		
		return user;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#updateCRUDEntity(com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	protected User updateCRUDEntity(User user) {
		user.setFirstName("Test");
		return user;
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#assertsAfterUpdate(com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	protected void assertsAfterUpdate(User user) {
		assertEquals("Test", user.getFirstName());
	}
	
}
