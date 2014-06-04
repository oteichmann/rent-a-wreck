package com.prodyna.pac.rentawreck.backend.rentable;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftType;
import com.prodyna.pac.rentawreck.backend.rentable.model.License;
import com.prodyna.pac.rentawreck.backend.rentable.service.LicenseService;

@RunWith(Arquillian.class)
@Transactional
public class LicenseTest extends AbstractBackendRentableTest<License> {

	@Inject
	private LicenseService service;
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<License> getService() {
		return service;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getCRUDEntity()
	 */
	@Override
	public License getCRUDEntity() {
		License license = new License();
		license.setUuid(UUID.randomUUID().toString());
		license.setAircraftType(AircraftType.TYPE_X);
		license.setValidTill(new Date());
		
		return license;
	}

}