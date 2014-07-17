package com.prodyna.pac.rentawreck.backend.resttest.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.rest.service.StringResponse;
import com.prodyna.pac.rentawreck.backend.rest.service.UtilService;
import com.prodyna.pac.rentawreck.backend.rest.service.impl.UtilServiceBean;
import com.prodyna.pac.rentawreck.backend.resttest.AbstractArquillianResteasyTest;
import com.prodyna.pac.rentawreck.backend.resttest.TestJaxRsActivator;

@RunWith(Arquillian.class)
public class ArquillianResteasySampleTest extends AbstractArquillianResteasyTest {
	
    @Deployment(testable = false)
    public static WebArchive create()
    {
    	WebArchive wa = ShrinkWrap.create(WebArchive.class, "raw-test.war");
    	wa.addClasses(TestJaxRsActivator.class, UtilService.class, UtilServiceBean.class, StringResponse.class);
        return wa;
    }

    @Test
    @RunAsClient
    @InSequence(0)
    public void generateUuidByWebTarget() {
    	
        WebTarget target = createWebTarget();
        Builder resource = target.path("/util/generate-uuid").request(MediaType.APPLICATION_JSON_TYPE);
        
        Response resp = resource.post(null);
        assertEquals( 200, resp.getStatus() );
        StringResponse stringResponse = resp.readEntity(StringResponse.class);
        assertNotNull(stringResponse);
        assertNotNull(stringResponse.getValue());
    }
    
    @Test
    @RunAsClient
    @InSequence(1)
    public void generateUuidByResteasyWebTarget() {
    	
    	ResteasyWebTarget target = createResteasyWebTarget();
        Builder resource = target.path("/util/generate-uuid").request(MediaType.APPLICATION_JSON_TYPE);
        
        Response resp = resource.post(null);
    	assertEquals( 200, resp.getStatus() );
    	StringResponse stringResponse = resp.readEntity(StringResponse.class);
    	assertNotNull(stringResponse);
    	assertNotNull(stringResponse.getValue());
    }

    @Test
    @RunAsClient
    @InSequence(2)
    public void generateUuidByResteasyWebTargetProxy() {
    	ResteasyWebTarget target = createResteasyWebTarget();
    	UtilService utilService = target.proxy(UtilService.class);
    	
        StringResponse response = utilService.gernerateUUID();
        assertNotNull(response);
        assertNotNull(response.getValue());
    }
}
