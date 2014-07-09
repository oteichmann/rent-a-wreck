package com.prodyna.pac.rentawreck.backend.common;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.service.RoleService;
import com.prodyna.pac.rentawreck.dbutil.DatabaseUtilScript;
import com.prodyna.pac.rentawreck.dbutil.DatabaseUtilService;
import com.prodyna.pac.rentawreck.dbutil.scripts.InitDatabase;

@RunWith(Arquillian.class)
@Transactional
public class DatabaseUtilTest {

	@Inject
	private DatabaseUtilService databaseUtilService;
	
	@Inject
	private RoleService roleService;

	@Inject
	private RoleService userService;
	
	@Deployment
	public static WebArchive createDeployment() {
		WebArchive wa = TestDeploymentFactory.getInstance().getBackendCommonDeployment();
		wa.addPackages(true, "com.prodyna.pac.rentawreck.dbutil");
		
		return wa;
	}
	
	@Test
	@InSequence(0)
	@Transactional(TransactionMode.ROLLBACK)
	public void testDatabaseUtilService() throws Exception {
		
		DatabaseUtilScript databaseUtilScript = new DatabaseUtilScript();
		databaseUtilScript.setSqlStatements(Arrays.asList(InitDatabase.INIT_DATABASE));
		databaseUtilService.executeDatabaseUtilScript(databaseUtilScript);
		
		assertEquals(new Integer(2), roleService.findAllCount());
		assertEquals(new Integer(1), userService.findAllCount());
	}

	
}
