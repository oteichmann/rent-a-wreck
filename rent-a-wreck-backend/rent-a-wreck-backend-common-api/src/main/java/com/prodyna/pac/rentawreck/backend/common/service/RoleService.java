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
	 * @param name
	 * @return
	 */
	Role findByName(String name);

}
