package com.prodyna.pac.rentawreck.backend.test.auth.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;
import com.prodyna.pac.rentawreck.backend.test.AbstractEntityCRUDTest;

@RunWith(Arquillian.class)
@Transactional
public class UserTest extends AbstractEntityCRUDTest<User> {

	@Inject
	private UserService userService;
	
	@Deployment
	public static WebArchive createDeployment() {
		return TestDeploymentFactory.getInstance().getBackendAuthDeployment();
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.test.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<User> getService() {
		return userService;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.test.common.AbstractEntityCRUDTest#getCRUDEntity()
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
	 * @see com.prodyna.pac.rentawreck.backend.test.common.AbstractEntityCRUDTest#updateCRUDEntity(com.prodyna.pac.rentawreck.backend.test.common.model.AbstractEntity)
	 */
	@Override
	protected User updateCRUDEntity(User user) {
		user.setFirstName("Test");
		return user;
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.test.common.AbstractEntityCRUDTest#assertsAfterUpdate(com.prodyna.pac.rentawreck.backend.test.common.model.AbstractEntity)
	 */
	@Override
	protected void assertsAfterUpdate(User user) {
		assertEquals("Test", user.getFirstName());
	}
	
	@Test
	@InSequence(1)
	@Transactional(TransactionMode.ROLLBACK)
	public void testFindByUsername(){
		User user = new User();
		user.setUuid(UUID.randomUUID().toString());
		user.setUsername("admin");
		user.setPassword("admin");
		user.setFirstName("Rent-A-Wreck");
		user.setLastName("Administrator");
		user.setEmail("admin@rent-a-wreck.com");
		
		userService.create(user);
		
		User findByUsername = userService.findByUsername("does-not-exist");
		assertNull(findByUsername);
		findByUsername = userService.findByUsername("admin");
		assertNotNull(findByUsername);
	}
	
	@Test
	@InSequence(2)
	@Transactional(TransactionMode.ROLLBACK)
	public void testUpdatePassword(){
		User user = new User();
		user.setUuid(UUID.randomUUID().toString());
		user.setUsername("admin");
		user.setPassword("admin");
		user.setFirstName("Rent-A-Wreck");
		user.setLastName("Administrator");
		user.setEmail("admin@rent-a-wreck.com");
		
		user = userService.create(user);
		System.err.println(user.getPassword());
		
		user.setPassword("test");
		User user2 = userService.update(user);
		System.err.println(user2.getPassword());
		
		User user3 = userService.updateUserPassword(user.getUuid(), "test");
		System.err.println(user3.getPassword());
	}
}
