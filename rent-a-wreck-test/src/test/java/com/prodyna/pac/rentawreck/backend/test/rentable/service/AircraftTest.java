package com.prodyna.pac.rentawreck.backend.test.rentable.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.UUID;

import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.model.Role;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftType;
import com.prodyna.pac.rentawreck.backend.rentable.service.AircraftService;
import com.prodyna.pac.rentawreck.backend.test.AbstractEntityCRUDTest;

@RunWith(Arquillian.class)
@Transactional
public class AircraftTest extends AbstractEntityCRUDTest<Aircraft>{

	@Inject
	private AircraftService aircraftService;

	@Deployment
	public static WebArchive createDeployment() {
		return TestDeploymentFactory.getInstance().getBackendRentableDeployment();
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.test.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<Aircraft> getService() {
		return aircraftService;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.test.common.AbstractEntityCRUDTest#getCRUDEntity()
	 */
	@Override
	public Aircraft createCRUDEntity() {
		Aircraft aircraft = new Aircraft();
		aircraft.setUuid(UUID.randomUUID().toString());
		aircraft.setId("B52");
		aircraft.setType(AircraftType.BOEING);
		
		return aircraft;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.test.common.AbstractEntityCRUDTest#updateCRUDEntity(com.prodyna.pac.rentawreck.backend.test.common.model.AbstractEntity)
	 */
	@Override
	protected Aircraft updateCRUDEntity(Aircraft aircraft) {
		aircraft.setId("B52");
		return aircraft;
	}
	
	@Test
	@InSequence(1)
	@Transactional(TransactionMode.ROLLBACK)
	public void testNullConstraints(){
		Aircraft aircraft = new Aircraft();
		aircraft.setUuid(UUID.randomUUID().toString());
		
		try {
			aircraftService.create(aircraft);
			fail("Should fail because of not null constraints.");
		} catch (EJBTransactionRolledbackException e) {
			assertTrue(e.getCause() instanceof ConstraintViolationException);
		}
	}
	
	@Test
	@InSequence(2)
	@Transactional(TransactionMode.ROLLBACK)
	public void testUniqueConstraints(){
		Aircraft aircraftA = new Aircraft();
		aircraftA.setUuid(UUID.randomUUID().toString());
		aircraftA.setId("B52");
		aircraftA.setType(AircraftType.BOEING);
		
		aircraftService.create(aircraftA);
		
		Aircraft aircraftB = new Aircraft();
		aircraftB.setUuid(UUID.randomUUID().toString());
		aircraftB.setId("B52");
		aircraftB.setType(AircraftType.BOEING);
		
		Role roleB = new Role();
		roleB.setUuid(UUID.randomUUID().toString());
		roleB.setName("test");
		try {
			aircraftService.create(aircraftB);
			fail("Should fail because of unique constraint violation.");
		} catch (EJBTransactionRolledbackException e) {
			assertTrue(e.getCause() instanceof PersistenceException);
		}
	}
}
