package com.prodyna.pac.rentawreck.backend.rentable;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest;
import com.prodyna.pac.rentawreck.backend.common.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.model.Role;
import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.common.service.RoleService;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftType;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;
import com.prodyna.pac.rentawreck.backend.rentable.model.CharterStatus;
import com.prodyna.pac.rentawreck.backend.rentable.model.Pilot;
import com.prodyna.pac.rentawreck.backend.rentable.service.AircraftService;
import com.prodyna.pac.rentawreck.backend.rentable.service.CharterService;
import com.prodyna.pac.rentawreck.backend.rentable.service.PilotService;

@RunWith(Arquillian.class)
@Transactional
public class CharterTest extends AbstractEntityCRUDTest<Charter> {

	@Inject
	private AircraftService aircraftService;
	
	@Inject
	private CharterService charterService;

	@Inject
	private PilotService pilotService;

	@Inject
	private UserService userService;
	
	@Inject
	private RoleService roleService;
	
	
	@Deployment
	public static WebArchive createDeployment() {
		return TestDeploymentFactory.getInstance().getBackendRentableDeployment();
	}
	
	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getService()
	 */
	@Override
	protected AbstractEntityPersistenceService<Charter> getService() {
		return charterService;
	}

	/* (non-Javadoc)
	 * @see com.prodyna.pac.rentawreck.backend.common.AbstractEntityCRUDTest#getCRUDEntity()
	 */
	@Override
	public Charter getCRUDEntity() {
		Aircraft aircraft = new Aircraft();
		aircraft.setUuid(UUID.randomUUID().toString());
		aircraft.setId("B52");
		aircraft.setType(AircraftType.BOEING);
		
		aircraftService.create(aircraft);
		
		Role role = new Role();
		role.setUuid(UUID.randomUUID().toString());
		role.setName("user");
		roleService.create(role);
		
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
		
		pilotService.create(pilot);
		
		Charter charter = new Charter();
		charter.setUuid(UUID.randomUUID().toString());
		charter.setAircraft(aircraft);
		charter.setPilot(pilot);
		charter.setCharterStatus(CharterStatus.RESERVED);
		return charter;
	}
	

	@Test
	@InSequence(1)
	@Transactional(TransactionMode.ROLLBACK)
	public void testFinderServiceOperations() {
		Aircraft aircraft = new Aircraft();
		aircraft.setUuid(UUID.randomUUID().toString());
		aircraft.setId("B52");
		aircraft.setType(AircraftType.BOEING);
		
		aircraftService.create(aircraft);
		
		Role role = new Role();
		role.setUuid(UUID.randomUUID().toString());
		role.setName("user");
		roleService.create(role);
		
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
		
		pilotService.create(pilot);
		
		Charter charter = new Charter();
		charter.setUuid(UUID.randomUUID().toString());
		charter.setCharterStatus(CharterStatus.RESERVED);
		charter.setCharterStart(new Date());
		charter.setCharterEnd(new Date());
		charter.setAircraft(aircraft);
		charter.setPilot(pilot);
		
		charterService.create(charter);
		
		List<Charter> aircraftCharters = charterService.getAircraftCharters(aircraft.getUuid());
		assertEquals(1, aircraftCharters.size());
		
		List<Charter> pilotCharters = charterService.getPilotCharters(pilot.getUuid());
		assertEquals(1, pilotCharters.size());
	}

}