package com.prodyna.pac.rentawreck.backend.common;

import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;

@RunWith(Arquillian.class)
@Transactional
public class UserTest extends AbstractBackendCommonTest<User> {

	@Inject
	private UserService service;

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
	public User getCRUDEntity() {
		User user = new User();
		user.setUuid(UUID.randomUUID().toString());
		user.setUsername("admin");
		user.setPassword("admin");
		user.setFirstName("Rent-A-Wreck");
		user.setLastName("Administrator");
		user.setEmail("admin@rent-a-wreck.com");
		
		return user;
	}
	
}
