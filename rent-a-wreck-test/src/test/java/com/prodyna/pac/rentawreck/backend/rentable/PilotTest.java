package com.prodyna.pac.rentawreck.backend.rentable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest;
import com.prodyna.pac.rentawreck.backend.common.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftType;
import com.prodyna.pac.rentawreck.backend.rentable.model.License;
import com.prodyna.pac.rentawreck.backend.rentable.model.Pilot;
import com.prodyna.pac.rentawreck.backend.rentable.service.PilotService;

@RunWith(Arquillian.class)
//@Transactional
public class PilotTest extends AbstractEntityCRUDTest<Pilot> {

	@Inject
	private PilotService pilotService;

	@Inject
	private UserService userService;
	
	@Deployment
	public static WebArchive createDeployment() {
		return TestDeploymentFactory.getInstance().getBackendRentableDeployment();
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<Pilot> getService() {
		return pilotService;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getCRUDEntity()
	 */
	@Override
	public Pilot createCRUDEntity() {
		
		User user = new User();
		user.setUuid(UUID.randomUUID().toString());
		user.setUsername("test");
		user.setPassword("test");
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test@rent-a-wreck.com");
		
		userService.create(user);
		
		Pilot pilot = new Pilot();
		pilot.setUuid(UUID.randomUUID().toString());
		pilot.setUser(user);
		
		License license = new License();
		license.setUuid(UUID.randomUUID().toString());
		license.setAircraftType(AircraftType.AIRBUS);
		license.setValidTill(new Date());
		
		Set<License> licenseSet = new HashSet<License>();
		licenseSet.add(license);
		
		pilot.setLicenses(licenseSet);
		
		return pilot;
	}
	

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#updateCRUDEntity(com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity)
	 */
	@Override
	protected Pilot updateCRUDEntity(Pilot pilot) {
		License license = new License();
		license.setUuid(UUID.randomUUID().toString());
		license.setAircraftType(AircraftType.BOEING);
		license.setValidTill(new Date());
		
		pilot.getLicenses().add(license);
		
		return pilot;
	}

}