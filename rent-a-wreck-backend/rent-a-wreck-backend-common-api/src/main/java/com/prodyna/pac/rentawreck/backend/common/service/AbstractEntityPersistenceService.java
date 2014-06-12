package com.prodyna.pac.rentawreck.backend.common.service;

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

import com.prodyna.pac.rentawreck.backend.common.model.AbstractEntity;

/**
 * Basic CRUD interface for all domain entities.
 *
 * @author Oliver Teichmann
 *
 * @param <T> A {@link AbstractEntity} implementation.
 */
//@SecurityDomain("rent-a-wreck")
public interface AbstractEntityPersistenceService<T extends AbstractEntity> {

	/**
	 * Persists a new entity.
	 * @param entity The new entity.
	 * @return The persisted version of the entity.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("admin")
	public T create(T entity);

	/**
	 * Reads the entity identified by the given identifier.
	 * @param uuid The identifier of the entity to read.
	 * @return The entity identified by the given identifier.
	 */
	@GET
	@Path("/{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public T read(@PathParam("uuid") String uuid);
	
	/**
	 * Updates an existing entity.
	 * @param entity The entity to update.
	 * @return The updated version of the entity.
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin"})
	public T update(T entity);

	/**
	 * Deletes the entity identified by the given identifier.
	 * @param uuid he identifier of the entity to delete.
	 */
	@DELETE
	@Path("/{uuid}")
	@RolesAllowed("admin")
	public void delete(@PathParam("uuid") String uuid);
	
	/**
	 * Gets the list of all entities of type <T>.
	 * @return The list of all entities.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<T> findAll();

	/**
	 * Gets the total number of entities of type <T>.
	 * @return The number of entities.
	 */
	@GET
	@Path("/count")
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public Integer findAllCount();

}
