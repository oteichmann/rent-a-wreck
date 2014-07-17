/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Pilot;

/**
 *  Service interface for the {@link Pilot} entity.
 *
 * @author Oliver Teichmann
 *
 */
@Path("/pilot")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PilotService extends AbstractEntityPersistenceService<Pilot> {
	
	/**
	 * Reads the pilot related to the given user.
	 * @param uuid The identifier of the user..
	 * @return The entity related to the give user.
	 */
	@GET
	@NoCache
	@Path("/user/{user_uuid}")
	@PermitAll
	public Pilot readPilotForUser(@PathParam("user_uuid") String userUuid);

}
