package com.prodyna.pac.rentawreck.backend.test.rentable.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.model.User;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;
import com.prodyna.pac.rentawreck.backend.common.util.DatabaseScripts;
import com.prodyna.pac.rentawreck.backend.common.util.DatabaseUtilScript;
import com.prodyna.pac.rentawreck.backend.common.util.DatabaseUtilService;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftCharterStatus;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;
import com.prodyna.pac.rentawreck.backend.rentable.model.License;
import com.prodyna.pac.rentawreck.backend.rentable.model.Pilot;
import com.prodyna.pac.rentawreck.backend.rentable.service.AircraftService;
import com.prodyna.pac.rentawreck.backend.rentable.service.CharterService;
import com.prodyna.pac.rentawreck.backend.rentable.service.LicenseService;
import com.prodyna.pac.rentawreck.backend.rentable.service.PilotService;

@RunWith(Arquillian.class)
@Transactional
public class AircraftCharterTest {

	@Inject
	private AircraftService aircraftService;

	@Inject
	private DatabaseUtilService databaseUtilService;
	
	@Inject
	private PilotService pilotService;

	@Inject
	private LicenseService licenseService;

	@Inject
	private UserService userService;

	@Inject
	private CharterService charterService;
	
	
	@Deployment
	public static WebArchive createDeployment() {
		WebArchive wa = TestDeploymentFactory.getInstance().getBackendRentableDeployment();
		wa.addPackages(true, "com.prodyna.pac.rentawreck.common.util");
		
		return wa;
	}

	@Test
	@InSequence(0)
	@Transactional(TransactionMode.ROLLBACK)
	public void testCharterStatusList(){
		
		DatabaseUtilScript databaseUtilScript = new DatabaseUtilScript();
		databaseUtilScript.addSqlStatements(DatabaseScripts.CREATE_ROLES_AND_USERS);
		databaseUtilScript.addSqlStatements(DatabaseScripts.CREATE_PILOTS);
		databaseUtilScript.addSqlStatements(DatabaseScripts.CREATE_AIRCRAFTS);
		databaseUtilScript.addSqlStatements(DatabaseScripts.CREATE_LICENSES);
		databaseUtilScript.addSqlStatements(DatabaseScripts.CREATE_CHARTERS);
		
		databaseUtilService.executeDatabaseUtilScript(databaseUtilScript);

		User adminUser = userService.findByUsername("admin");
		Pilot adminPilot = pilotService.readPilotForUser(adminUser.getUuid());

		Iterator<License> iterator = adminPilot.getLicenses().iterator();
		while (iterator.hasNext()) {
			License license = (License) iterator.next();
			GregorianCalendar validTill = new GregorianCalendar();
			validTill.add(Calendar.MONTH, 1);
			license.setValidTill(validTill.getTime());
			licenseService.update(license);
		}
		
		List<Charter> pilotCharters = charterService.getPilotCharters(adminPilot.getUuid());
		Charter pilotCharter = pilotCharters.get(0);
		GregorianCalendar charterStart = new GregorianCalendar();
		pilotCharter.setCharterStart(charterStart.getTime());
		GregorianCalendar charterEnd = new GregorianCalendar();
		charterEnd.add(Calendar.DATE, 1);
		pilotCharter.setCharterEnd(charterEnd.getTime());
		charterService.update(pilotCharter);
		
		List<AircraftCharterStatus> aircraftCharterStatusList = aircraftService.getAircraftCharterStatusList();
		
		for (AircraftCharterStatus aircraftCharterStatus : aircraftCharterStatusList) {
			if(aircraftCharterStatus.getAircraft().equals(pilotCharter.getAircraft())) {
				assertFalse(aircraftCharterStatus.getAvailable());
			} else {
				assertTrue(aircraftCharterStatus.getAvailable());
			}
		}
	}


}
