package com.prodyna.pac.rentawreck.backend.resttest;

import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class AbstractArquillianResteasyTest {

    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";
    
	@ArquillianResource
	private URL deploymentURL;
	
    protected WebTarget createWebTarget() {
    	Client client = ClientBuilder.newClient();
    	client.register(new BasicAuthenticationClientRequestFilter(USERNAME, PASSWORD));
        WebTarget target = client.target(deploymentURL.toString() + "rest");
        return target;
    }
    
    protected ResteasyWebTarget createResteasyWebTarget() {
    	ResteasyClient client = new ResteasyClientBuilder().build();
    	client.register(new BasicAuthenticationClientRequestFilter(USERNAME, PASSWORD));
    	ResteasyWebTarget target = client.target(deploymentURL.toString() + "rest");
    	return target;
    }
    
    protected ResteasyWebTarget createUnauthenticatedResteasyWebTarget() {
    	ResteasyClient client = new ResteasyClientBuilder().build();
    	ResteasyWebTarget target = client.target(deploymentURL.toString() + "rest");
    	return target;
    }
    
}