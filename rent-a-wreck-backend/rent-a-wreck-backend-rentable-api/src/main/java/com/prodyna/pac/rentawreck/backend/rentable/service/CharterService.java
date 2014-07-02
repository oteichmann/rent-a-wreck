/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.common.service.rest.DateFormat;
import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;
import com.prodyna.pac.rentawreck.backend.rentable.model.CharterStatus;

/**
 * CharterService
 *
 * @author Oliver Teichmann
 *
 */
@Path("/charter")
public interface CharterService extends AbstractEntityPersistenceService<Charter> {
	
	/**
	 * Create a new charter for a pilot.
	 * @param entity The new entity.
	 * @return The persisted version of the entity.
	 */
	@POST
	@Path("/create/{uuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"user" , "admin"})
	public Charter createCharter(@PathParam("uuid") String uuid, Charter charter);
	
	@PUT
	@Path("/{uuid}/dates")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"user" , "admin"})
	public Charter updateCharterDates(@PathParam("uuid") String uuid,
			@QueryParam("charterStart") @DateFormat("yyyy-MM-dd") Date charterStart, @QueryParam("charterEnd") @DateFormat("yyyy-MM-dd") Date charterEnd);
	
	@PUT
	@Path("/{uuid}/state")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"user" , "admin"})
	public Charter updateCharterStatus(@PathParam("uuid") String uuid,
			@QueryParam("newCharterStatus") CharterStatus newCharterStatus);
	
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
