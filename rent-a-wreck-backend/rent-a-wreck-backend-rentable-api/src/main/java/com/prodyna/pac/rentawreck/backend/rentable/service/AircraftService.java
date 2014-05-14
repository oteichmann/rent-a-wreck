/**
 * 
 */
package com.prodyna.pac.rentawreck.backend.rentable.service;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.ejb3.annotation.SecurityDomain;

import com.prodyna.pac.rentawreck.backend.rentable.model.Aircraft;

/**
 * @author oteichmann
 */
@Path("/aircrafts")
@SecurityDomain("rent-a-wreck")
public interface AircraftService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("admin")
	public Aircraft create(Aircraft aircraft);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin"})
	public Aircraft update(Aircraft aircraft);

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	@RolesAllowed("admin")
	public void delete(@PathParam("id") long id);

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Aircraft findById(@PathParam("id") long id);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin", "user"})
	public List<Aircraft> findAll();

	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public int findAllCount();
}
