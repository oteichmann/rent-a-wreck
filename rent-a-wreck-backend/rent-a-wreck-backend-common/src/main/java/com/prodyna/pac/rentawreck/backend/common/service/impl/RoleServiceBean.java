package com.prodyna.pac.rentawreck.backend.common.service.impl;

import java.util.logging.Logger;

import javax.inject.Inject;

import com.prodyna.pac.rentawreck.backend.common.model.Role;
import com.prodyna.pac.rentawreck.backend.common.service.RoleService;

/**
 *  Implementation of the {@link RoleService} interface.
 * 
 * @author Oliver Teichmann
 * 
 */
public class RoleServiceBean extends AbstractEntityPersistenceServiceBean<Role> implements RoleService {

	@Inject
	private Logger log;

	@Override
	protected Class<Role> getEntityClass() {
		return Role.class;
	}

	@Override
	protected Logger getLooger() {
		return log;
	}

}
