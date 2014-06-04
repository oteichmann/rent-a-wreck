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

import com.prodyna.pac.rentawreck.backend.common.service.AbstractEntityPersistenceService;
import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;

/**
 * @author oteichmann
 */
@Path("/aircrafts")
public interface AircraftService extends AbstractEntityPersistenceService<Aircraft> {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<Aircraft> findAll();

	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public int findAllCount();
}
