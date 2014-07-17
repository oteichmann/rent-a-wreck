package com.prodyna.pac.rentawreck.backend.resttest;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import com.prodyna.pac.rentawreck.backend.rest.service.AuthenticationServiceConstants;

public class TokenAuthenticationClientRequestFilter implements ClientRequestFilter {

    private String token;

    public TokenAuthenticationClientRequestFilter(String token) {
		this.token = token;
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        clientRequestContext.getHeaders().add(AuthenticationServiceConstants.X_XSRF_TOKEN, token);
    }

}
