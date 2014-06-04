package com.prodyna.pac.rentawreck.backend.rentable;

import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;
import com.prodyna.pac.rentawreck.backend.rentable.model.CharterStatus;
import com.prodyna.pac.rentawreck.backend.rentable.service.CharterService;

@RunWith(Arquillian.class)
@Transactional
public class CharterTest extends AbstractBackendRentableTest<Charter> {

	@Inject
	private CharterService service;
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<Charter> getService() {
		return service;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getCRUDEntity()
	 */
	@Override
	public Charter getCRUDEntity() {
		Charter charter = new Charter();
		charter.setUuid(UUID.randomUUID().toString());
		charter.setAircraftUuid(UUID.randomUUID().toString());
		charter.setPilotUuid(UUID.randomUUID().toString());
		charter.setCharterStatus(CharterStatus.RESERVED);
		return charter;
	}

}