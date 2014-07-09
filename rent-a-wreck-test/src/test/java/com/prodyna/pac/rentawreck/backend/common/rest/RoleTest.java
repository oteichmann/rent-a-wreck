/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common.rest;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.UUID;

import javax.ws.rs.NotAuthorizedException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.model.Role;
import com.prodyna.pac.rentawreck.backend.common.service.RoleService;
import com.prodyna.pac.rentawreck.backend.rest.JaxRsActivator;
import com.prodyna.pac.rentawreck.dbutil.DatabaseUtilScript;
import com.prodyna.pac.rentawreck.dbutil.DatabaseUtilServiceREST;
import com.prodyna.pac.rentawreck.dbutil.scripts.InitDatabase;

/**
 * RoleTest
 * 
 * @author Oliver Teichmann
 * 
 */
@RunWith(Arquillian.class)
//@Transactional
public class RoleTest extends AbstractArquillianResteasyTest {
	
	@Deployment(testable=false)
	public static WebArchive createDeployment() {
		WebArchive wa = TestDeploymentFactory.getInstance().getBackendCommonDeployment();
		wa.addPackages(true, "com.prodyna.pac.rentawreck.backend.rest");
		wa.addPackages(true, "com.prodyna.pac.rentawreck.dbutil");
		wa.deleteClass(JaxRsActivator.class);
		wa.addClass(TestJaxRsActivator.class);
		
		System.out.println(wa.toString(true));
		return wa;
	}
	
	@Before
	public void initDatabase() {
		DatabaseUtilServiceREST databaseUtilService = createResteasyWebTarget().proxy(DatabaseUtilServiceREST.class);
		DatabaseUtilScript databaseUtilScript = new DatabaseUtilScript();
		databaseUtilScript.setSqlStatements(Arrays.asList(InitDatabase.INIT_DATABASE));
		try {
			databaseUtilService.executeDatabaseUtilScript(databaseUtilScript);
		} catch (Throwable t) {
			System.err.println(t.getMessage());
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
			Role persistedRole = roleService.create(role);
			fail("Should fail because of missing authentication");
		} catch (NotAuthorizedException e) {
			assertEquals(401, e.getResponse().getStatus());
		}
//		assertEquals(3, roleService.findAllCount().intValue());
//		
//		persistedRole.setName("test1");
//		
//		Role updatedRole = roleService.update(persistedRole);
//		assertEquals("test1", updatedRole.getName());
//		
//		roleService.delete(updatedRole.getUuid());
//		
//		assertNull(roleService.read(updatedRole.getUuid()));
//		assertEquals(2, roleService.findAllCount().intValue());
	}

}
