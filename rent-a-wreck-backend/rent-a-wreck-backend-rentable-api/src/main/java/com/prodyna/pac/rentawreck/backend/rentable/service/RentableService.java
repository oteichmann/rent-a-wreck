/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.rentawreck.backend.rentable.model.Rentable;

/**
 * @author oteichmann
 *
 */
@Path("/rentables")
public interface RentableService {
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
	public Rentable fetchById(long id);
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Rentable> fetchAll();

}
