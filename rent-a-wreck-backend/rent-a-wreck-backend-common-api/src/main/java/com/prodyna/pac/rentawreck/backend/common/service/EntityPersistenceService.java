package com.prodyna.pac.rentawreck.backend.common.service;

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

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;

/**
 * Basis crud interface for all RAW entities.
 * 
 * @author oteichmann
 *
 * @param <T>
 */
public interface EntityPersistenceService<T extends AbstractEntity> {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("admin")
	public T create(T entity);

	@GET
	@Path("/{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public T findById(@PathParam("uuid") String uuid);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin"})
	public T update(T entity);

	@DELETE
	@Path("/{uuid}")
	@RolesAllowed("admin")
	public void delete(@PathParam("uuid") String uuid);

}
