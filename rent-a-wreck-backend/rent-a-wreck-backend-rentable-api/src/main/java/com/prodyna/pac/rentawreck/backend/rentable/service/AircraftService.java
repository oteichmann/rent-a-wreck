/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftCharterStatus;

/**
 * Service interface for the {@link Aircraft} entity.
 *
 * @author Oliver Teichmann
 *
 */
@Path("/aircraft")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AircraftService extends AbstractEntityPersistenceService<Aircraft> {
	
	/**
	 * Reads all aircrafts and returns them as a list of {@link AircraftCharterStatus} objects.
	 * @return
	 */
	@GET
	@NoCache
	@Path("/status-list")
	@PermitAll
	List<AircraftCharterStatus> getAircraftCharterStatusList();

}
