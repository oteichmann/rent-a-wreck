package com.prodyna.pac.rentawreck.backend.rest.service;

import javax.annotation.security.PermitAll;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * UtilService
 *
 * @author Oliver Teichmann, PRODYNA AG
 *
 */
@Path("/util")
public interface UtilService {

	@POST
	@Path("/generate-uuid")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public StringResponse gernerateUUID();
}
