package com.prodyna.pac.rentawreck;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;
import com.prodyna.pac.rentawreck.backend.rentable.service.AircraftService;

@RunWith(Arquillian.class)
public class AircraftTest {

	@Inject
	@Named("aircraftServiceBean")
	private AircraftService service;

	@Deployment
	public static WebArchive createDeployment() {
		WebArchive wa = ShrinkWrap.create(WebArchive.class, "test.war");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend", "com.prodyna.pac.rentawreck.aircraft");
		wa.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		// Deploy our test datasource
		wa.addAsWebInfResource("test-ds.xml", "test-ds.xml");
		System.out.println(wa.toString(true));
		return wa;
	}

	@Test
	public void simpleTest() {
		service.findAll();
		assertEquals(0, service.findAllCount());
		Aircraft instance0 = new Aircraft();
		instance0.setName("B52");
		Aircraft instance1 = service.create(instance0);
		Assert.assertNotNull(instance1.getId());
		assertEquals(1, service.findAllCount());
		service.delete(instance1.getId());
		assertEquals(0, service.findAllCount());
	}

}
