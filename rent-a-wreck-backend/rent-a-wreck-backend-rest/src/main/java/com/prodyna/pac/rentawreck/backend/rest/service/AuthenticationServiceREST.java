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

import com.prodyna.pac.rentawreck.backend.common.service.AuthenticationService;
import com.prodyna.pac.rentawreck.backend.rest.service.request.LoginRequest;
import com.prodyna.pac.rentawreck.backend.rest.service.request.TokenRequest;

/**
 * REST facade of {@link AuthenticationService}.
 *
 * @author Oliver Teichmann
 *
 */
@Path("/auth")
@SecurityDomain("rent-a-wreck")
public interface AuthenticationServiceREST {
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response login(LoginRequest loginRequest);
	
	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response validateToken(TokenRequest tokenRequest);
	
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin","user"})
	public Response logout(TokenRequest tokenRequest);
}
