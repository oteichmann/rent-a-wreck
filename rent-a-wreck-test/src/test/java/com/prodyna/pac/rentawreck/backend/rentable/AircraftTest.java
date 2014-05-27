package com.prodyna.pac.rentawreck.backend.rentable;

import static org.junit.Assert.assertEquals;

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

import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;
import com.prodyna.pac.rentawreck.backend.rentable.service.AircraftService;

@RunWith(Arquillian.class)
@Transactional
public class AircraftTest {

	@Inject
	private AircraftService service;

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.common",  "com.prodyna.pac.rentawreck.backend.rentable");
		wa.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		wa.addAsResource("META-INF/beans.xml");
		wa.addAsWebInfResource("test-ds.xml", "test-ds.xml");
		System.out.println(wa.toString(true));     
		
		return wa;
	}

	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void simpleTest() {
		service.findAll();
		assertEquals(0, service.findAllCount());
		Aircraft instance0 = new Aircraft();
		instance0.setUuid(UUID.randomUUID().toString());
		instance0.setName("B52");
		Aircraft instance1 = service.create(instance0);
		Assert.assertNotNull(instance1.getUuid());
		assertEquals(1, service.findAllCount());
		service.delete(instance1.getUuid());
		assertEquals(0, service.findAllCount());
	}

}
