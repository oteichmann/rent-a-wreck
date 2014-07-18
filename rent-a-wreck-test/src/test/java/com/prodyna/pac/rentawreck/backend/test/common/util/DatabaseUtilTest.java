package com.prodyna.pac.rentawreck.backend.test.common.util;

import static org.junit.Assert.assertEquals;

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
import com.prodyna.pac.rentawreck.backend.common.service.RoleService;
import com.prodyna.pac.rentawreck.backend.common.service.UserService;
import com.prodyna.pac.rentawreck.backend.util.DatabaseScripts;
import com.prodyna.pac.rentawreck.backend.util.DatabaseUtilScript;
import com.prodyna.pac.rentawreck.backend.util.DatabaseUtilService;

@RunWith(Arquillian.class)
@Transactional
public class DatabaseUtilTest {

	@Inject
	private DatabaseUtilService databaseUtilService;
	
	@Inject
	private RoleService roleService;

	@Inject
	private UserService	 userService;
	
	@Deployment
	public static WebArchive createDeployment() {
		WebArchive wa = TestDeploymentFactory.getInstance().getBackendAuthDeployment();
		wa.addPackages(true, "com.prodyna.pac.rentawreck.common.util");
		
		return wa;
	}
	
	@Test
	@InSequence(0)
	@Transactional(TransactionMode.ROLLBACK)
	public void testDatabaseUtilService() throws Exception {
		
		DatabaseUtilScript databaseUtilScript = new DatabaseUtilScript();
		databaseUtilScript.addSqlStatements(DatabaseScripts.CREATE_ROLES_AND_USERS);
		databaseUtilService.executeDatabaseUtilScript(databaseUtilScript);
		
		assertEquals(new Integer(2), roleService.findAllCount());
		assertEquals(new Integer(2), userService.findAllCount());
	}

	
}
