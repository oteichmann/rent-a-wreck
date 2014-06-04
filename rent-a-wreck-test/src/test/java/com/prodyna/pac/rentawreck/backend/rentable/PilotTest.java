package com.prodyna.pac.rentawreck.backend.rentable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftType;
import com.prodyna.pac.rentawreck.backend.rentable.model.CharterStatus;
import com.prodyna.pac.rentawreck.backend.rentable.model.License;
import com.prodyna.pac.rentawreck.backend.rentable.model.Pilot;
import com.prodyna.pac.rentawreck.backend.rentable.service.LicenseService;
import com.prodyna.pac.rentawreck.backend.rentable.service.PilotService;

@RunWith(Arquillian.class)
@Transactional
public class PilotTest extends AbstractBackendRentableTest<Pilot> {

	@Inject
	private PilotService service;
	
	@Inject
	private LicenseService licenseService;
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<Pilot> getService() {
		return service;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getCRUDEntity()
	 */
	@Override
	public Pilot getCRUDEntity() {
		Pilot pilot = new Pilot();
		pilot.setUuid(UUID.randomUUID().toString());
		pilot.setUserUuid(UUID.randomUUID().toString());
		
		License license = new License();
		license.setUuid(UUID.randomUUID().toString());
		license.setAircraftType(AircraftType.TYPE_X);
		license.setValidTill(new Date());
		
		Set<License> licenseSet = new HashSet<License>();
		licenseSet.add(license);
		
		pilot.setLicenseSet(licenseSet);
		
		return pilot;
	}

}