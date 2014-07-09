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

import org.jboss.resteasy.annotations.cache.NoCache;

import com.prodyna.pac.rentawreck.backend.common.model.Role;

/**
 * Service interface for the {@link Role} entity.
 *  
 * @author Oliver Teichmann
 *
 */
@Path("/role")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface RoleService extends AbstractEntityPersistenceService<Role>{

	/**
	 * Persists a new entity.
	 * @param entity The new entity.
	 * @return The persisted version of the entity.
	 */
	@POST
	@Path("/{uuid}")
	@RolesAllowed("admin")
	public Role create(@PathParam("uuid") String uuid, Role entity);
	
	/**
	 * Persists a new entity.
	 * @param entity The new entity.
	 * @return The persisted version of the entity.
	 */
	@POST
	@Path("/hidden-create")
	@RolesAllowed("admin")
	public Role create(Role entity);

	/**
	 * Reads the entity identified by the given identifier.
	 * @param uuid The identifier of the entity to read.
	 * @return The entity identified by the given identifier.
	 */
	@GET
	@NoCache
	@Path("/{uuid}")
	@PermitAll
	public Role read(@PathParam("uuid") String uuid);
	
	/**
	 * Updates an existing entity.
	 * @param entity The entity to update.
	 * @return The updated version of the entity.
	 */
	@PUT
	@Path("/{uuid}")
	@RolesAllowed({"admin"})
	public Role update(@PathParam("uuid") String uuid, Role entity);

	/**
	 * Updates an existing entity.
	 * @param entity The entity to update.
	 * @return The updated version of the entity.
	 */
	@PUT
	@Path("/hidden-update")
	@RolesAllowed("admin")
	public Role update(Role entity);
	
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
	@NoCache
	@PermitAll
	public List<Role> findAll();

	/**
	 * Gets the total number of entities of type <T>.
	 * @return The number of entities.
	 */
	@GET
	@NoCache
	@Path("/count")
	@Produces(MediaType.TEXT_PLAIN)
	@PermitAll
	public Integer findAllCount();
	/**
	 * Find a role by name.
	 * @param name Name of the role.
	 * @return The corresponding role object.
	 */
	@GET
	@Path("/name/{name}")
	Role findByName(@PathParam("name") String name);

}
