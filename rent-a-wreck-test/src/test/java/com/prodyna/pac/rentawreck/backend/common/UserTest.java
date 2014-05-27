package com.prodyna.pac.rentawreck.backend.common;

import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;

@RunWith(Arquillian.class)
@Transactional
public class UserTest {

	@Inject
	private UserService service;
	
	@Deployment
	public static WebArchive createDeployment() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend");
		wa.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		wa.addAsResource("META-INF/beans.xml");
		wa.addAsWebInfResource("test-ds.xml", "test-ds.xml");
		System.out.println(wa.toString(true));     
		
		return wa;
	}

	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void simpleTest() {
//		service.findAll();
//		assertEquals(0, service.findAllCount());
		User instance0 = new User();
		instance0.setUuid(UUID.randomUUID().toString());
		instance0.setUsername("admin");
		instance0.setPassword("admin");
		instance0.setFirstName("Rent-A-Wreck");
		instance0.setLastName("Administrator");
		instance0.setEmail("admin@rent-a-wreck.com");
		User instance1 = service.create(instance0);
		Assert.assertNotNull(instance1.getUuid());
//		Assert.assertNotEquals(instance0.getPassword(), instance1.getPassword());
//		assertEquals(1, service.findAllCount());
		service.delete(instance1.getUuid());
//		assertEquals(0, service.findAllCount());
	}

}
