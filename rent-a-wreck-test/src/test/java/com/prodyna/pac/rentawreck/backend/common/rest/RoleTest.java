/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.common.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.net.URL;
import java.util.UUID;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.model.Role;
import com.prodyna.pac.rentawreck.backend.common.service.RoleService;
import com.prodyna.pac.rentawreck.backend.rest.JaxRsActivator;

/**
 * RoleTest
 * 
 * @author Oliver Teichmann
 * 
 */
@RunWith(Arquillian.class)
//@Transactional
public class RoleTest {
	
	@ArquillianResource
	private URL deploymentURL;

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		WebArchive webArchive = TestDeploymentFactory.getInstance().getBackendCommonDeployment();
		webArchive.addPackages(true, "com.prodyna.pac.rentawreck.backend.rest");
		webArchive.deleteClass(JaxRsActivator.class);
		webArchive.addClass(TestJaxRsActivator.class);
		return webArchive;
	}

	@Test
	@InSequence(0)
//	@Transactional(TransactionMode.ROLLBACK)
	public void testCRUDOperations(@ArquillianResteasyResource RoleService roleService){
		
		Role role = new Role();
		role.setUuid(UUID.randomUUID().toString());
		role.setName("user");
		
		assertNull(roleService.read(role.getUuid()));
		assertEquals(0, roleService.findAllCount().intValue());
		
		Role persistedRole = roleService.create(role);
		assertEquals(1, roleService.findAllCount().intValue());
		
		persistedRole.setName("admin");
		Role updatedRole = roleService.update(role);
		
		roleService.delete(updatedRole.getUuid());
		
		assertNull(roleService.read(updatedRole.getUuid()));
		assertEquals(0, roleService.findAllCount().intValue());
		
	}
	
//	/**
//	 * We can inject either proxy or a ResteasyWebTarget for low level manipulations and assertions.
//	 *
//	 * @param webTarget configured resource ready for use, injected by Arquillian
//	 */
//	@Test
//	@InSequence(1)
//	public void createPackageBareRsource(@ArquillianResteasyResource("rest/role") ResteasyWebTarget webTarget)
//	{
//	    //        Given
//	    final Invocation.Builder invocationBuilder = webTarget.request();
//	    invocationBuilder.acceptEncoding("UTF-8");
//	    invocationBuilder.accept(MediaType.APPLICATION_ATOM_XML_TYPE);
//	    invocationBuilder.header("Authorization","Basic sialala");
//	    final Invocation invocation = invocationBuilder.buildPost(Entity.entity("{\"biskupa\":\"?upa\"}", MediaType.APPLICATION_JSON_TYPE));
//	 
//	    //        When
//	    final Response response = invocation.invoke();
//	 
//	    //        Then
//	    assertEquals(deploymentURL + "rest/customer", webTarget.getUri().toASCIIString());
//	    assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
//	    assertEquals(HttpStatus.SC_OK, response.getStatus());
//	}

}
