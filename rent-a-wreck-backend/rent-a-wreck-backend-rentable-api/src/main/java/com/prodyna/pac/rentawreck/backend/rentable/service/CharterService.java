/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;

/**
 * CharterService
 *
 * @author Oliver Teichmann
 *
 */
@Path("/charter")
public interface CharterService extends AbstractEntityPersistenceService<Charter> {

	@GET
	@NoCache
	@Path("/aircraft/{aircraft_uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	List<Charter> getAircraftCharters(@PathParam("aircraft_uuid") String aircraftUuid);
	
	@GET
	@NoCache
	@Path("/pilot/{pilot_uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	List<Charter> getPilotCharters(@PathParam("pilot_uuid") String pilotUuid);

	@GET
	@NoCache
	@Path("/aircraft/active/{aircraft_uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	Charter getActiveAircraftCharter(@PathParam("aircraft_uuid") String aircraftUuid);

	@GET
	@NoCache
	@Path("/overdue")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	List<Charter> getOverdueCharters();

}
