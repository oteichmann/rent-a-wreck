package com.prodyna.pac.rentawreck.backend.common.rest;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class ClientAuthenticationSetter implements ClientRequestFilter {

    private String auth;

    public ClientAuthenticationSetter(String username, String password) {
//        auth = "Basic " + new Base64Codec().encode(String.format("%s:%s", username, password).getBytes());
    }

    @Override
    public void filter(ClientRequestContext clientRequestContext) throws IOException {
        clientRequestContext.getHeaders().add("Authorization", auth);
    }

}
