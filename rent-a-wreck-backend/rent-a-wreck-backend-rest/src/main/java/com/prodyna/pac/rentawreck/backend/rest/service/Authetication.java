package com.prodyna.pac.rentawreck.backend.rest.service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.ejb3.annotation.SecurityDomain;

@Path("/login")
@SecurityDomain("rent-a-wreck")
public interface Authetication {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin", "user"})
	@AuthenticationResponse
	public Response login();

}
