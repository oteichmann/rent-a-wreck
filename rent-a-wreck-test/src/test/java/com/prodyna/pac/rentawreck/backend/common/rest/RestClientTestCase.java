package com.prodyna.pac.rentawreck.backend.common.rest;


import java.net.URL;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.rentawreck.backend.common.util.Producer;
import com.prodyna.pac.rentawreck.backend.rest.service.StringResponse;
import com.prodyna.pac.rentawreck.backend.rest.service.UtilService;
import com.prodyna.pac.rentawreck.backend.rest.service.impl.UtilServiceBean;

@RunWith(Arquillian.class)
public class RestClientTestCase {
 
    @ArquillianResource
    private URL deploymentURL;
    
    @Deployment(testable = false)
    public static WebArchive create()
    {
        return ShrinkWrap.create(WebArchive.class)
            .addClasses(TestJaxRsActivator.class, UtilService.class, UtilServiceBean.class, StringResponse.class);
    }
 
    /**
     * Arquillian calculates resource path by using deployment URL+ArquillianResteasyResource.value which is by default "rest".
     * If your API is located under different root i.e. "api_v2" then use @ArquillianResteasyResource("api_v2")
     *
     * @param customerResource configured resource ready for use, injected by Arquillian
     */
    @Test
    public void getCustomerById(@ArquillianResteasyResource UtilService utilService)
    {
    	StringResponse response = utilService.gernerateUUID();
    	
    	Assert.assertNotNull(response);
    	Assert.assertNotNull(response.getValue());
    	System.out.println(response.getValue());
    	
    }
}