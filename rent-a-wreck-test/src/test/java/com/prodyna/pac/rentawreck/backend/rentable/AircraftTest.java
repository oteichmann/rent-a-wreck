package com.prodyna.pac.rentawreck.backend.rentable;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest;
import com.prodyna.pac.rentawreck.backend.common.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftType;
import com.prodyna.pac.rentawreck.backend.rentable.service.AircraftService;

@RunWith(Arquillian.class)
@Transactional
public class AircraftTest extends AbstractEntityCRUDTest<Aircraft>{

	@Inject
	private AircraftService service;
	
	@Deployment
	public static WebArchive createDeployment() {
		return TestDeploymentFactory.getInstance().getBackendRentableDeployment();
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<Aircraft> getService() {
		return service;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getCRUDEntity()
	 */
	@Override
	public Aircraft getCRUDEntity() {
		Aircraft aircraft = new Aircraft();
		aircraft.setUuid(UUID.randomUUID().toString());
		aircraft.setId("B52");
		aircraft.setType(AircraftType.TYPE_X);
		
		return aircraft;
	}

	@Test
	@InSequence(1)
	@Transactional(TransactionMode.ROLLBACK)
	public void simpleTest() {
		service.findAll();
		assertEquals(0, service.findAllCount().intValue());
		Aircraft instance0 = new Aircraft();
		instance0.setUuid(UUID.randomUUID().toString());
		instance0.setId("B52");
		instance0.setType(AircraftType.TYPE_X);
		Aircraft instance1 = service.create(instance0);
		Assert.assertNotNull(instance1.getUuid());
		assertEquals(1, service.findAllCount().intValue());
		service.delete(instance1.getUuid());
		assertEquals(0, service.findAllCount().intValue());
	}

}
