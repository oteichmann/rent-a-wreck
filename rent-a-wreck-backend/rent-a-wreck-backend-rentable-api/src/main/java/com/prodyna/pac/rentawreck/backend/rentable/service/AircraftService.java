/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;
import com.prodyna.pac.rentawreck.backend.rentable.model.AircraftCharterStatus;

/**
 * @author oteichmann
 */
@Path("/aircraft")
public interface AircraftService extends AbstractEntityPersistenceService<Aircraft> {
	
	@GET
	@NoCache
	@Path("/status-list")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	List<AircraftCharterStatus> getAircraftCharterStatusList();

}
