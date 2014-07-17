package com.prodyna.pac.rentawreck.backend.test.rentable.service;

import java.util.GregorianCalendar;
import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftType;
import com.prodyna.pac.rentawreck.backend.rentable.model.License;
import com.prodyna.pac.rentawreck.backend.rentable.service.LicenseService;
import com.prodyna.pac.rentawreck.backend.test.AbstractEntityCRUDTest;

@RunWith(Arquillian.class)
@Transactional
public class LicenseTest extends AbstractEntityCRUDTest<License> {

	@Inject
	private LicenseService service;
	
	@Deployment
	public static WebArchive createDeployment() {
		return TestDeploymentFactory.getInstance().getBackendRentableDeployment();
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.test.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<License> getService() {
		return service;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.test.common.AbstractEntityCRUDTest#getCRUDEntity()
	 */
	@Override
	public License createCRUDEntity() {
		License license = new License();
		license.setUuid(UUID.randomUUID().toString());
		license.setAircraftType(AircraftType.AIRBUS);
		license.setValidTill(new GregorianCalendar(2014,01,30).getTime());
		
		return license;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.test.common.AbstractEntityCRUDTest#updateCRUDEntity(com.prodyna.pac.rentawreck.backend.test.common.model.AbstractEntity)
	 */
	@Override
	protected License updateCRUDEntity(License license) {
		license.setValidTill(new GregorianCalendar(2014,02,30).getTime());
		
		return license;
	}

}