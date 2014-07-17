package com.prodyna.pac.rentawreck.backend.common.service;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.rentawreck.backend.common.model.User;

/**
 * Service interface for the {@link User} entity.
 * 
 * @author Oliver Teichmann
 * 
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UserService extends AbstractEntityPersistenceService<User> {

	/**
	 * Updates the password of a user.
	 * 
	 * @param uuid The identifier of the user to update.
	 * @param password The new password.
	 * @return The user with the updated password.
	 */
	@PUT
	@Path("/update/")
	@RolesAllowed("admin")
	User updateUserPassword(@QueryParam("uuid") String uuid, @QueryParam("password") String password);
	
	/**
	 * Find a user by username.
	 * @param name Username of the user.
	 * @return The corresponding user object.
	 */
	User findByUsername(String username);
}
