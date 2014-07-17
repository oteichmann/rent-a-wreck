package com.prodyna.pac.rentawreck.backend.auth.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.prodyna.pac.rentawreck.backend.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.model.Role;
import com.prodyna.pac.rentawreck.backend.common.service.RoleService;
import com.prodyna.pac.rentawreck.backend.common.util.DatabaseScripts;
import com.prodyna.pac.rentawreck.backend.common.util.DatabaseUtilScript;
import com.prodyna.pac.rentawreck.backend.common.util.rest.DatabaseUtilServiceREST;
import com.prodyna.pac.rentawreck.backend.rest.AbstractArquillianResteasyTest;
import com.prodyna.pac.rentawreck.backend.rest.JaxRsActivator;
import com.prodyna.pac.rentawreck.backend.rest.TestJaxRsActivator;

/**
 * RoleTest
 * 
 * @author Oliver Teichmann
 * 
 */
@RunWith(Arquillian.class)
public class RoleTest extends AbstractArquillianResteasyTest {
	
	@Inject
	private Logger logger;
	
	@Deployment(testable=false)
	public static WebArchive createDeployment() {
		WebArchive wa = TestDeploymentFactory.getInstance().getBackendAuthDeployment();
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.rest");
		wa.deleteClass(JaxRsActivator.class);
		wa.addClass(TestJaxRsActivator.class);
		
		return wa;
	}
	
	@Before
	public void initDatabase() {
		DatabaseUtilServiceREST databaseUtilService = createResteasyWebTarget().proxy(DatabaseUtilServiceREST.class);
		DatabaseUtilScript databaseUtilScript = new DatabaseUtilScript();
		databaseUtilScript.addSqlStatements(DatabaseScripts.CREATE_ROLES_AND_USERS);
		try {
			databaseUtilService.executeDatabaseUtilScript(databaseUtilScript);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
	}

    @Test
    @RunAsClient
    @InSequence(0)
    public void testCRUDOperationsWithWebTargetProxy() {
    	
    	RoleService roleService = createResteasyWebTarget().proxy(RoleService.class);

		assertEquals(2, roleService.findAllCount().intValue());

		Role role = new Role();
		role.setUuid(UUID.randomUUID().toString());
		role.setName("test");
		
		assertNull(roleService.read(role.getUuid()));
		
		Role persistedRole = roleService.create(role);
		assertEquals(3, roleService.findAllCount().intValue());
		
		persistedRole.setName("test1");
		
		Role updatedRole = roleService.update(persistedRole);
		assertEquals("test1", updatedRole.getName());
		
		roleService.delete(updatedRole.getUuid());
		
		assertNull(roleService.read(updatedRole.getUuid()));
		assertEquals(2, roleService.findAllCount().intValue());
    }
    
	@Test
	@InSequence(1)
	public void testCRUDOperationsWithArquillianResteasyResource(@ArquillianResteasyResource RoleService roleService){
		
		assertEquals(2, roleService.findAllCount().intValue());

		Role role = new Role();
		role.setUuid(UUID.randomUUID().toString());
		role.setName("test");
		
		assertNull(roleService.read(role.getUuid()));
		
		try {
			roleService.create(role);
			fail("Should fail because of missing authentication.");
		} catch (NotAuthorizedException e) {
			assertEquals(401, e.getResponse().getStatus());
		}
		
		role = roleService.findAll().get(0);
		role.setName("test");
		
		try {
			roleService.update(role);
			fail("Should fail because of missing authentication.");
		} catch (NotAuthorizedException e) {
			assertEquals(401, e.getResponse().getStatus());
		}
		
		try {
			roleService.delete(role.getUuid());
			fail("Should fail because of missing authentication.");
		} catch (NotAuthorizedException e) {
			assertEquals(401, e.getResponse().getStatus());
		}
	}

}
