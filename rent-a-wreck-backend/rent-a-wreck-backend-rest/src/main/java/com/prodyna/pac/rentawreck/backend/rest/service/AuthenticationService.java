package com.prodyna.pac.rentawreck.backend.rest.service;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.ejb3.annotation.SecurityDomain;

import com.prodyna.pac.rentawreck.backend.rest.model.AuthenticationRequest;
import com.prodyna.pac.rentawreck.backend.rest.model.TokenRequest;
import com.prodyna.pac.rentawreck.backend.rest.model.TokenSubject;


@Path("/auth")
@SecurityDomain("rent-a-wreck")
public interface AuthenticationService {
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response login(AuthenticationRequest authenticationRequest);
	
	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin","user"})
	public Response validateToken(TokenRequest tokenRequest);
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin","user"})
	public Response logout(TokenRequest tokenRequest);

	public TokenSubject getTokenSubject(String token);

}
