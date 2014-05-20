package com.prodyna.pac.rentawreck.backend.rest.service;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.ejb3.annotation.SecurityDomain;

import com.prodyna.pac.rentawreck.backend.rest.model.AuthenticationRequest;
import com.prodyna.pac.rentawreck.backend.rest.model.TokenSubject;


@Path("/login")
@SecurityDomain("rent-a-wreck")
public interface AuthenticationService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	@AuthenticationResponse
	public Response login(AuthenticationRequest authenticationRequest);

	public TokenSubject getTokenSubject(String token);

}
