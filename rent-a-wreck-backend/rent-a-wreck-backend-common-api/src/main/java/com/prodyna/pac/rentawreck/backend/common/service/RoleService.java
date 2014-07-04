package com.prodyna.pac.rentawreck.backend.common.service;

import javax.ws.rs.Path;

import com.prodyna.pac.rentawreck.backend.common.model.Role;

/**
 * Service interface for the {@link Role} entity.
 *  
 * @author Oliver Teichmann
 *
 */
@Path("/role")
public interface RoleService extends AbstractEntityPersistenceService<Role>{

	/**
	 * Find a role by name.
	 * @param name Name of the role.
	 * @return The corresponding role object.
	 */
	Role findByName(String name);

}
