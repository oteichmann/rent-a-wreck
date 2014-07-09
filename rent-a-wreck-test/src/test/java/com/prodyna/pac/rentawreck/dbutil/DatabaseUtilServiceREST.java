package com.prodyna.pac.rentawreck.dbutil;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * DBUtilService
 *
 * @author Oliver Teichmann
 *
 */
@Path("/dbutil")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface DatabaseUtilServiceREST {

	@POST
	@Path("/script")
	@PermitAll
    Response executeDatabaseUtilScript(DatabaseUtilScript script);
	
	@POST
	@Path("/scripts")
	@PermitAll
	Response executeDatabaseUtilScripts(List<DatabaseUtilScript> scripts);
}
