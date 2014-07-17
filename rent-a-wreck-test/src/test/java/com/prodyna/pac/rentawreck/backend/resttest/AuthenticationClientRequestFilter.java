package com.prodyna.pac.rentawreck.backend.resttest;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.jboss.security.Base64Encoder;

public class AuthenticationClientRequestFilter implements ClientRequestFilter {

    private String auth;

    public AuthenticationClientRequestFilter(String username, String password) {
        try {
			auth = "Basic " + Base64Encoder.encode(String.format("%s:%s", username, password).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        clientRequestContext.getHeaders().add("Authorization", auth);
    }

}
