package com.prodyna.pac.rentawreck.backend.resttest.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import com.prodyna.pac.rentawreck.backend.TestDeploymentFactory;
import com.prodyna.pac.rentawreck.backend.common.util.DatabaseScripts;
import com.prodyna.pac.rentawreck.backend.common.util.DatabaseUtilScript;
import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationServiceConstants;
import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationServiceREST;
import com.prodyna.pac.rentawreck.backend.rest.service.request.LoginRequest;
import com.prodyna.pac.rentawreck.backend.rest.service.request.TokenRequest;
import com.prodyna.pac.rentawreck.backend.resttest.AbstractArquillianResteasyTest;
import com.prodyna.pac.rentawreck.backend.resttest.DatabaseUtilServiceREST;
import com.prodyna.pac.rentawreck.backend.resttest.TokenAuthenticationClientRequestFilter;

/**
 * RoleTest
 * 
 * @author Oliver Teichmann
 * 
 */
@RunWith(Arquillian.class)
public class AuthenticationServiceRESTTest extends AbstractArquillianResteasyTest {
	
	@Inject
	private Logger logger;
	
	@Deployment(testable=false)
	public static WebArchive createDeployment() {
		WebArchive wa = TestDeploymentFactory.getInstance().getBackendAuthDeploymentREST();
		
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
    public void testAuthenticationService() {
    	ResteasyWebTarget webTarget = createUnauthenticatedResteasyWebTarget();
    	AuthenticationServiceREST authService = webTarget.proxy(AuthenticationServiceREST.class);
    	
    	// test login with invalid credentials
    	LoginRequest loginRequest = new LoginRequest();
    	loginRequest.setUsername("admin");
    	loginRequest.setPassword("invalid");
		Response authServiceResponse = authService.login(loginRequest);
		
		assertEquals(403, authServiceResponse.getStatus());
		
		authServiceResponse.close();
		
		// test login with valid credentials
    	loginRequest.setUsername("admin");
    	loginRequest.setPassword("admin");
    	authServiceResponse = authService.login(loginRequest);
		
		assertEquals(200, authServiceResponse.getStatus());
		
		Map<String, NewCookie> cookies = authServiceResponse.getCookies();
		NewCookie authenticationToken = cookies.get(AuthenticationServiceConstants.XSRF_TOKEN);
		assertNotNull(authenticationToken);
		assertNotNull(authenticationToken.getValue());
		String token = authenticationToken.getValue();
		
		authServiceResponse.close();
		
		// test token validation
		TokenRequest tokenRequest = new TokenRequest();
		tokenRequest.setToken(token);
		authServiceResponse = authService.validateToken(tokenRequest);
		
		assertEquals(200, authServiceResponse.getStatus());
		
		authServiceResponse.close();
		
		// test logout without authentication token in header
		authServiceResponse = authService.logout(tokenRequest);
		assertEquals(401, authServiceResponse.getStatus());
		
		authServiceResponse.close();
	
		// set authentication token into header
		webTarget.getResteasyClient().register(new TokenAuthenticationClientRequestFilter(token));

		// test logout
		authServiceResponse = authService.logout(tokenRequest);
		assertEquals(200, authServiceResponse.getStatus());

		authServiceResponse.close();

		// test token validation - should be invalidated
		authServiceResponse = authService.validateToken(tokenRequest);
		
		assertEquals(401, authServiceResponse.getStatus());
		
		authServiceResponse.close();
				
    }

}
