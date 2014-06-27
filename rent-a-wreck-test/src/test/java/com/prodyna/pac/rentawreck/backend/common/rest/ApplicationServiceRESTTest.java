package com.prodyna.pac.rentawreck.backend.common.rest;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ApplicationServiceRESTTest extends AbstractRESTTest {

    @Test
    @RunAsClient
    @InSequence(1)
    public void resetScenario() {
        // this part is without dynamic proxy
        WebTarget target = createWebTarget();
        Response resp = target.path("/util/generate-uuid").request(MediaType.APPLICATION_JSON_TYPE).get();
        Assert.assertEquals( 204, resp.getStatus() );
    }

//    @Test
//    @RunAsClient
//    @InSequence(2)
//    public void applicationInfo() {
//        ApplicationService as = createService(ApplicationService.class);
//        Application ai = as.getApplicationInfo();
//        Assert.assertEquals("read-from-mongo", ai.getName());
//    }
}
